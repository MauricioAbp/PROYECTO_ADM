import { ChangeDetectorRef, Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { LucideAngularModule, Flame, LogOut, ChevronRight, ShieldCheck, Eye, EyeOff, Store, Bell, LayoutGrid, ShoppingBag, ChefHat, Printer, CircleDollarSign, WalletCards, LayoutDashboard, Plus, Clock3, Search, Minus, ReceiptText, CookingPot, History, FileText, PackageCheck, X, Boxes, Users, CalendarDays, BarChart3, Settings, Edit3, RefreshCcw, TrendingUp, LockKeyhole, Trash2, Database, Archive, Download } from 'lucide-angular';

type Product = {id:number; name:string; desc:string; price:number; cat:string; sold:boolean};
type CartItem = Product & {qty:number};
type Catalogo = {id:number; codigo:string|null; nombre:string};
type ProductoApi = {id:number;nombre:string;descripcion:string|null;precio:number;categoria:Catalogo;areaPreparacion:Catalogo;unidadesNegocio:Catalogo[];disponible:boolean;activo:boolean};
type PaginaProductos = {content:ProductoApi[];totalElements:number};
type UsuarioSesion = {id:number;username:string;nombres:string;apellidos:string;rolCodigo:string;rolNombre:string;activo:boolean};
type LoginResponse = {token:string;tipo:string;usuario:UsuarioSesion;permisos:string[]};

@Component({selector:'app-root',standalone:true,imports:[FormsModule,LucideAngularModule],templateUrl:'./app.component.html'})
export class AppComponent {
  private readonly http:HttpClient;
  private readonly apiUrl='http://localhost:8080/api/v1';
  constructor(http:HttpClient,private readonly cdr:ChangeDetectorRef){this.http=http;}
  readonly icons={Flame,LogOut,ChevronRight,ShieldCheck,Eye,EyeOff,Store,Bell,LayoutGrid,ShoppingBag,ChefHat,Printer,CircleDollarSign,WalletCards,LayoutDashboard,Plus,Clock3,Search,Minus,ReceiptText,CookingPot,History,FileText,PackageCheck,X,Boxes,Users,CalendarDays,BarChart3,Settings,Edit3,RefreshCcw,TrendingUp,LockKeyhole,Trash2,Database,Archive,Download};
  logged=false; unit:string|null=null; page='mesas'; toast=''; modal=''; showPassword=false;
  username=''; password=''; loginError=''; loggingIn=false; token=''; currentUser:UsuarioSesion|null=null; permissions:string[]=[];
  tableFilter='Todas'; category='Todos'; query=''; station='Todas'; payment='Efectivo'; shiftOpen=true;
  adminTab='dashboard'; adminUnit='Consolidado'; adminSegment='stock';
  readonly states=[['Disponible','free'],['Ocupada','busy'],['Pedido enviado','sent'],['En preparación','cooking'],['Listo','ready'],['Pendiente de pago','payment']];
  readonly nav=[['mesas',LayoutGrid,'Mesas'],['pedido',ShoppingBag,'Pedidos'],['cocina',ChefHat,'Cocina'],['comandas',Printer,'Comandas'],['caja',CircleDollarSign,'Caja'],['turno',WalletCards,'Apertura y cierre'],['admin',LayoutDashboard,'Administración']];
  readonly adminMenu=[['dashboard',LayoutDashboard,'Dashboard'],['usuarios',Users,'Usuarios y permisos'],['productos',ShoppingBag,'Productos'],['menu',CalendarDays,'Mesas y menú'],['inventario',Boxes,'Inventario y recetas'],['ventas',FileText,'Ventas e historial'],['anulaciones',History,'Cancelaciones'],['bi',BarChart3,'Business Intelligence'],['config',Settings,'Configuración']];
  readonly products:Product[]=[['Menú ejecutivo','Entrada + plato + refresco',18,'Menús'],['Ceviche clásico','Pescado, camote y choclo',32,'Ceviches'],['Arroz con mariscos','Mariscos y salsa criolla',30,'Arroces'],['Lomo saltado','Lomo, papas y arroz',28,'Platos'],['Pollo a la brasa','1/4 pollo, papas y ensalada',22,'Pollos'],['Chicha morada','Jarra personal 500 ml',7,'Bebidas'],['Tequeños','6 unidades con guacamole',14,'Entradas'],['Suspiro limeño','Porción individual',10,'Postres']].map((x,i)=>({id:i+1,name:String(x[0]),desc:String(x[1]),price:Number(x[2]),cat:String(x[3]),sold:i===7}));
  cart:CartItem[]=[{...this.products[0],qty:1}];
  readonly categories=['Todos','Frecuentes','Menús','Entradas','Ceviches','Arroces','Platos','Pollos','Bebidas','Postres'];
  readonly kitchenOrders=[{id:'#184',table:'Mesa 04',unit:'Restaurante',time:'18 min',late:true,w:'María P.',status:'Nuevo',station:'Ceviches',items:['2× Ceviche clásico · Sin ají','1× Arroz con mariscos']},{id:'#183',table:'Mesa 09',unit:'Restaurante',time:'12 min',late:false,w:'Diego R.',status:'En preparación',station:'Cocina',items:['1× Lomo saltado · Término medio','2× Chicha morada · Sin hielo']},{id:'#182',table:'Llevar 017',unit:'Pollería',time:'07 min',late:false,w:'María P.',status:'Nuevo',station:'Horno',items:['2× Pollo a la brasa · Papas separadas','1× Tequeños']}];
  readonly printRows=[['#184-A','Mesa 04','Ceviches','Impresa'],['#184-B','Mesa 04','Arroces','Fallida'],['#183-A','Mesa 09','Cocina','Enviada'],['#182-A','Llevar 017','Horno','Pendiente'],['#181-A','Mesa 02','Barra','Reimpresa']];
  readonly users=[['María Paredes','Mesera','Restaurante','Hoy, 07:18','Activo'],['Diego Rojas','Mesero','Ambos','Hoy, 07:24','Activo'],['Claudia Soto','Caja','Restaurante','Hoy, 07:11','Activo'],['Luis Cárdenas','Cocina','Ambos','Ayer, 18:42','Activo'],['Ana Torres','Administradora','Ambos','30 ago, 10:05','Inactivo']];
  readonly adminProducts=[['Menú ejecutivo','Menús','S/ 18.00','Restaurante','Cocina','Activo'],['Ceviche clásico','Ceviches','S/ 32.00','Ambos','Ceviches','Activo'],['Pollo a la brasa','Pollos','S/ 22.00','Pollería','Horno','Activo'],['Arroz con mariscos','Arroces','S/ 30.00','Restaurante','Arroces','Activo'],['Suspiro limeño','Postres','S/ 10.00','Ambos','Postres','Agotado']];
  apiProducts:ProductoApi[]=[];
  apiCategories:Catalogo[]=[];
  apiAreas:Catalogo[]=[];
  loadingProducts=false;
  savingProduct=false;
  editingProductId:number|null=null;
  productError='';
  productForm={nombre:'',descripcion:'',precio:null as number|null,categoriaId:null as number|null,areaPreparacionId:null as number|null,restaurante:true,polleria:false,disponible:true};
  readonly stock=[['Pollo entero','Unidad','38','20','S/ 14.50','Normal'],['Papa amarilla','kg','12.5','15','S/ 3.20','Stock bajo'],['Pescado fresco','kg','7.2','10','S/ 18.00','Stock bajo'],['Arroz','kg','42','15','S/ 4.10','Normal'],['Aceite vegetal','litro','8','6','S/ 8.50','Normal']];
  readonly sales=[['V-002184','31/08 12:48','Restaurante','Mesa 04','María P.','Efectivo','S/ 78.00','Pagada'],['V-002183','31/08 12:36','Restaurante','Mesa 09','Diego R.','Yape','S/ 116.00','Pagada'],['V-002182','31/08 12:21','Pollería','Llevar 017','María P.','Tarjeta','S/ 94.00','Pagada'],['V-002181','31/08 11:58','Restaurante','Mesa 02','Diego R.','Efectivo','S/ 64.00','Anulada']];
  get tables(){return Array.from({length:13},(_,i)=>({id:i+1,state:this.states[i%6][0],tone:this.states[i%6][1],waiter:i%3?'María P.':'Diego R.',time:i%6?`${8+i*3} min`:'—'}));}
  get filteredTables(){return this.tables.filter(t=>this.tableFilter==='Todas'||t.state===this.tableFilter);}
  get filteredProducts(){const q=this.query.toLowerCase();return this.products.filter(p=>(this.category==='Todos'||this.category==='Frecuentes'||p.cat===this.category)&&p.name.toLowerCase().includes(q));}
  get filteredKitchen(){return this.kitchenOrders.filter(o=>this.station==='Todas'||o.station===this.station);}
  get total(){return this.cart.reduce((s,x)=>s+x.price*x.qty,0);}
  get itemCount(){return this.cart.reduce((s,x)=>s+x.qty,0);}
  get adminTitle(){return this.adminMenu.find(x=>x[0]===this.adminTab)?.[2]??'Dashboard';}
  money(n:number){return `S/ ${n.toFixed(2)}`;} pad(n:number){return String(n).padStart(2,'0');}
  get displayName(){return this.currentUser?`${this.currentUser.nombres} ${this.currentUser.apellidos}`:'Usuario';}
  get initials(){return this.currentUser?`${this.currentUser.nombres[0]??''}${this.currentUser.apellidos[0]??''}`.toUpperCase():'U';}
  get canManageCatalog(){return this.permissions.includes('CATALOGO_GESTIONAR');}
  private authOptions(){return {headers:{Authorization:`Bearer ${this.token}`}};}
  login(){
    this.loginError='';
    if(!this.username.trim()||!this.password){this.loginError='Ingresa tu usuario y contraseña.';return;}
    this.loggingIn=true;
    this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`,{username:this.username.trim(),password:this.password}).subscribe({
      next:r=>{this.token=r.token;this.currentUser=r.usuario;this.permissions=r.permisos;this.logged=true;this.password='';this.loggingIn=false;this.loadProductCatalogs();this.cdr.detectChanges();},
      error:e=>{this.loggingIn=false;this.loginError=e.status===401?'Usuario o contraseña incorrectos, o la cuenta está desactivada.':'No se pudo conectar con el backend.';this.cdr.detectChanges();}
    });
  }
  logout(){this.logged=false;this.unit=null;this.page='mesas';this.token='';this.currentUser=null;this.permissions=[];this.password='';}
  add(p:Product){if(p.sold)return;const x=this.cart.find(i=>i.id===p.id);if(x)x.qty++;else this.cart=[...this.cart,{...p,qty:1}];}
  changeQty(id:number,d:number){this.cart=this.cart.map(x=>x.id===id?{...x,qty:x.qty+d}:x).filter(x=>x.qty>0);}
  notify(message:string){this.toast=message;window.setTimeout(()=>{if(this.toast===message)this.toast='';},3500);}
  confirmOrder(){this.modal='';this.notify('Pedido #185 enviado a cocina');}
  confirmPayment(){this.modal='';this.notify('Pago registrado · Mesa 04 liberada');}
  toggleShift(){const closing=this.shiftOpen;this.shiftOpen=!this.shiftOpen;this.notify(closing?'Turno cerrado correctamente':'Caja abierta correctamente');}
  adminRows():string[][]{if(this.adminTab==='usuarios')return this.users;if(this.adminTab==='productos')return this.apiProducts.map(p=>[p.nombre,p.categoria.nombre,this.money(Number(p.precio)),p.unidadesNegocio.length===2?'Ambos':p.unidadesNegocio.map(u=>u.nombre).join(', '),p.areaPreparacion.nombre,!p.activo?'Inactivo':p.disponible?'Activo':'Agotado']);if(this.adminTab==='inventario')return this.stock;return this.sales;}
  adminHeads():string[]{if(this.adminTab==='usuarios')return ['Usuario','Rol','Unidad','Último acceso','Estado'];if(this.adminTab==='productos')return ['Producto','Categoría','Precio','Unidad','Área','Estado'];if(this.adminTab==='inventario')return ['Insumo','Unidad','Stock','Stock mínimo','Costo','Estado'];return ['Venta','Fecha','Unidad','Origen','Mesero','Método','Total','Estado'];}
  loadProductCatalogs(){
    this.loadingProducts=true;this.productError='';
    this.http.get<Catalogo[]>(`${this.apiUrl}/categorias?activo=true`,this.authOptions()).subscribe({next:x=>{this.apiCategories=x;this.cdr.detectChanges();},error:e=>{if(e.status===401)this.logout();this.productError='No se pudieron cargar las categorías. Verifica tu sesión.';this.cdr.detectChanges();}});
    this.http.get<Catalogo[]>(`${this.apiUrl}/catalogos/areas-preparacion`,this.authOptions()).subscribe({next:x=>{this.apiAreas=x;this.cdr.detectChanges();},error:e=>{if(e.status===401)this.logout();this.productError='No se pudieron cargar las áreas de preparación.';this.cdr.detectChanges();}});
    this.loadProducts();
  }
  loadProducts(){this.loadingProducts=true;this.http.get<PaginaProductos>(`${this.apiUrl}/productos?size=100&sort=nombre`,this.authOptions()).subscribe({next:x=>{this.apiProducts=x.content;this.loadingProducts=false;this.cdr.detectChanges();},error:e=>{if(e.status===401)this.logout();this.loadingProducts=false;this.productError='No se pudieron cargar los productos. Verifica tu sesión.';this.cdr.detectChanges();}});}
  openProductForm(){this.editingProductId=null;this.productError='';this.productForm={nombre:'',descripcion:'',precio:null,categoriaId:this.apiCategories[0]?.id??null,areaPreparacionId:this.apiAreas[0]?.id??null,restaurante:true,polleria:false,disponible:true};this.modal='product';}
  openProductEdit(index:number){const p=this.apiProducts[index];if(!p)return;this.editingProductId=p.id;this.productError='';this.productForm={nombre:p.nombre,descripcion:p.descripcion??'',precio:Number(p.precio),categoriaId:p.categoria.id,areaPreparacionId:p.areaPreparacion.id,restaurante:p.unidadesNegocio.some(u=>u.codigo==='RESTAURANTE'),polleria:p.unidadesNegocio.some(u=>u.codigo==='POLLERIA'),disponible:p.disponible};this.modal='product';}
  async saveProduct(){
    const f=this.productForm;const unidades=[f.restaurante?'RESTAURANTE':'',f.polleria?'POLLERIA':''].filter(Boolean);
    if(!f.nombre.trim()||!f.precio||f.precio<=0||!f.categoriaId||!f.areaPreparacionId||!unidades.length){this.productError='Completa nombre, precio, categoría, área y al menos una unidad de negocio.';return;}
    this.savingProduct=true;this.productError='';
    try{
      const editing=this.editingProductId!==null;const url=editing?`${this.apiUrl}/productos/${this.editingProductId}`:`${this.apiUrl}/productos`;
      const response=await fetch(url,{method:editing?'PUT':'POST',headers:{'Content-Type':'application/json','Authorization':`Bearer ${this.token}`},body:JSON.stringify({nombre:f.nombre.trim(),descripcion:f.descripcion.trim()||null,precio:f.precio,categoriaId:f.categoriaId,areaPreparacionId:f.areaPreparacionId,unidadesNegocio:unidades,disponible:f.disponible,activo:true})});
      const body=await response.json();
      if(!response.ok)throw new Error(body?.detail??'No se pudo registrar el producto.');
      const producto=body as ProductoApi;this.modal='';this.notify(`Producto ${producto.nombre} ${editing?'actualizado':'registrado'}`);this.editingProductId=null;this.loadProducts();
    }catch(error){this.productError=error instanceof Error?error.message:'No se pudo registrar el producto.';}finally{this.savingProduct=false;this.cdr.detectChanges();}
  }
}
