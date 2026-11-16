-- ============================================================
-- TRABALHO PRÁTICO 04 - CBTLPR2 (Java) - ADS 471
-- Professor: Wellington Tuler Moraes
-- Script de criação do banco SQL Server: aulajava
-- ============================================================

-- 1. Criação do banco de dados aulajava
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'aulajava')
BEGIN
    CREATE DATABASE aulajava;
END
GO

USE aulajava;
GO

-- Limpeza caso as tabelas já existam (respeitando a ordem da FK)
IF OBJECT_ID('dbo.tbfuncs', 'U') IS NOT NULL
    DROP TABLE dbo.tbfuncs;
GO

IF OBJECT_ID('dbo.tbcargos', 'U') IS NOT NULL
    DROP TABLE dbo.tbcargos;
GO

-- 2. Criação da tabela tbcargos
CREATE TABLE tbcargos (
    cd_cargo SMALLINT NOT NULL,
    ds_cargo CHAR(20) NULL,
    CONSTRAINT PK_tbcargos PRIMARY KEY (cd_cargo)
);
GO

-- 3. Criação da tabela tbfuncs com chave estrangeira para tbcargos
CREATE TABLE tbfuncs (
    cod_func DECIMAL(9, 0) NOT NULL,
    nome_func CHAR(30) NULL,
    sal_func MONEY NULL,
    cod_cargo SMALLINT NULL,
    CONSTRAINT PK_tbfuncs PRIMARY KEY (cod_func),
    CONSTRAINT FK_tbfuncs_tbcargos FOREIGN KEY (cod_cargo)
        REFERENCES tbcargos (cd_cargo)
);
GO

-- 4. Inserção de registros na tabela tbcargos
INSERT INTO tbcargos (cd_cargo, ds_cargo) VALUES
(1, 'Administrativo'),
(2, 'Desenvolvedor'),
(3, 'Analista de Sistemas'),
(4, 'Gerente de Projetos'),
(5, 'Suporte Tecnico');
GO

-- 5. Inserção de registros na tabela tbfuncs (respeitando PK e FK)
INSERT INTO tbfuncs (cod_func, nome_func, sal_func, cod_cargo) VALUES
(1, 'Marcelo', 2000.00, 1),
(2, 'Marcelo Santos', 3500.00, 2),
(3, 'Marcela Oliveira', 4200.50, 3),
(4, 'Ana Paula Souza', 5100.00, 3),
(5, 'Carlos Eduardo', 7500.00, 4),
(6, 'Beatriz Lima', 2800.00, 5),
(7, 'Marcos Vinicius', 3800.00, 2);
GO

-- Consulta de verificação com JOIN (mesma lógica usada no Java)
SELECT 
    f.cod_func,
    RTRIM(f.nome_func) AS nome_func,
    f.sal_func,
    RTRIM(c.ds_cargo) AS ds_cargo
FROM tbfuncs f
INNER JOIN tbcargos c ON f.cod_cargo = c.cd_cargo;
GO
