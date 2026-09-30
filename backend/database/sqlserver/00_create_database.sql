/* Ejecutar una sola vez con una cuenta autorizada para crear bases de datos. */
IF DB_ID(N'LaRamadita') IS NULL
BEGIN
    CREATE DATABASE LaRamadita;
END;
GO

ALTER DATABASE LaRamadita SET READ_COMMITTED_SNAPSHOT ON;
GO
