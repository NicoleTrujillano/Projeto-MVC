-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: projeto_mvc
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aluno`
--

DROP TABLE IF EXISTS `aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aluno` (
  `rgm` int(11) NOT NULL,
  `nome_aluno` varchar(50) DEFAULT NULL,
  `data_nascimento` varchar(15) DEFAULT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `endereco` varchar(50) DEFAULT NULL,
  `municipio` varchar(25) DEFAULT NULL,
  `UF` char(2) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `id_curso` int(11) DEFAULT NULL,
  PRIMARY KEY (`rgm`),
  KEY `fk_aluno_curso` (`id_curso`),
  CONSTRAINT `fk_aluno_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id_curso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aluno`
--

LOCK TABLES `aluno` WRITE;
/*!40000 ALTER TABLE `aluno` DISABLE KEYS */;
INSERT INTO `aluno` VALUES (1001,'Mariana da Silva','12/03/2002','123.456.789-00','mariana.silva@email.com','Rua das Rosas, 120','Campinas','SP','(11) 99999-0001',11),(1002,'João Pedro Alves','25/06/2001','321.654.987-11',' joao.alves@email.com','Av. Central, 456','São Paulo','SP','(11) 98888-0002',12),(1003,'Letícia Ramos Costa','08/11/2003','456.123.789-22','leticia.costa@email.com','Rua Bela Vista, 789','Sorocaba','RJ','(15) 97777-0003',13),(123456,'João da Silva Sauro','29/02/2000','123.456.789-00','joao@email.com','Rua Exemplo, 123','Ribeirão Preto','RS','(11) 91234-5678',1),(654321,'Teste 2','11/11/1111','111.222.333-44','email@teste.com','Av. Teste, 700','Guarulhos','MG','(11) 91234-5678',4),(2222222,'Pedro Junior','24/05/2013','343.565.787-99','pedrinjunior@email.com','Rua 25 de Março','UmaCidadeAi','SC','(11) 22222-2222',10);
/*!40000 ALTER TABLE `aluno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso` (
  `id_curso` int(11) NOT NULL AUTO_INCREMENT,
  `nome_curso` varchar(50) DEFAULT 'Analise e Desenvolvimento de Sistemas',
  `campus` varchar(25) DEFAULT 'Guarulhos',
  `periodo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'Análise e Desenvolvimento de Sistemas','Guarulhos','Noturno'),(4,'Logística','Carapicuíba','Matutino'),(10,'Comércio Exterior','Mauá','Vespertino'),(11,'Análise e Desenvolvimento de Sistemas','Tatuapé','Matutino'),(12,'Comércio Exterior','São Paulo','Noturno'),(13,'Logística','Mauá','Vespertino');
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `desempenho`
--

DROP TABLE IF EXISTS `desempenho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `desempenho` (
  `id_desempenho` int(11) NOT NULL AUTO_INCREMENT,
  `id_aluno` int(11) DEFAULT NULL,
  `id_disciplina` int(11) DEFAULT NULL,
  `semestre` varchar(20) DEFAULT NULL,
  `nota` decimal(5,2) DEFAULT NULL,
  `faltas` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_desempenho`),
  KEY `id_aluno` (`id_aluno`),
  KEY `id_disciplina` (`id_disciplina`),
  CONSTRAINT `desempenho_ibfk_1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`rgm`),
  CONSTRAINT `desempenho_ibfk_2` FOREIGN KEY (`id_disciplina`) REFERENCES `disciplina` (`id_disciplina`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `desempenho`
--

LOCK TABLES `desempenho` WRITE;
/*!40000 ALTER TABLE `desempenho` DISABLE KEYS */;
INSERT INTO `desempenho` VALUES (1,123456,10,'2025-1',3.00,8),(2,654321,14,'2023-2',10.00,10),(5,123456,11,'2024-2',4.00,0),(10,123456,12,'2020-1',3.00,16),(12,1001,10,'2025-1',9.00,8),(13,1001,12,'2020-1',6.00,16);
/*!40000 ALTER TABLE `desempenho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `disciplina` (
  `id_disciplina` int(11) NOT NULL AUTO_INCREMENT,
  `nome_disciplina` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_disciplina`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplina`
--

LOCK TABLES `disciplina` WRITE;
/*!40000 ALTER TABLE `disciplina` DISABLE KEYS */;
INSERT INTO `disciplina` VALUES (10,'Programação Orientada a Objetos'),(11,'Banco de Dados'),(12,'Engenharia de Software III'),(13,'Gestão de Estoques'),(14,'Transporte e Distribuição'),(15,'Logística Internacional'),(16,'Economia Internacional'),(17,'Legislação Aduaneira'),(18,'Negócios Internacionais');
/*!40000 ALTER TABLE `disciplina` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-13  2:52:09
