-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Hoszt: 127.0.0.1
-- Létrehozás ideje: 2015. Nov 01. 12:42
-- Szerver verzió: 5.6.17
-- PHP verzió: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Adatbázis: `dictdb`
--
CREATE DATABASE IF NOT EXISTS `dictdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `dictdb`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `languages`
--

CREATE TABLE IF NOT EXISTS `languages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- A tábla adatainak kiíratása `languages`
--

INSERT INTO `languages` (`id`, `Name`) VALUES
(1, 'Magyar'),
(2, 'Angol');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `words`
--

CREATE TABLE IF NOT EXISTS `words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Word` varchar(45) NOT NULL,
  `Meaning` int(11) DEFAULT NULL,
  `Language` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Words_Words_idx` (`Meaning`),
  KEY `fk_Words_Languages1_idx` (`Language`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- A tábla adatainak kiíratása `words`
--

INSERT INTO `words` (`id`, `Word`, `Meaning`, `Language`) VALUES
(1, 'car', 6, 2),
(2, 'water', 4, 2),
(3, 'blue', 5, 2),
(4, 'víz', 2, 1),
(5, 'kék', 3, 1),
(6, 'autó', 1, 1),
(7, 'szomorú', 3, 1),
(8, 'blue', 7, 2),
(9, 'robot', 10, 1),
(10, 'robot', 9, 2);

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `words`
--
ALTER TABLE `words`
  ADD CONSTRAINT `fk_Words_Words` FOREIGN KEY (`Meaning`) REFERENCES `words` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Words_Languages1` FOREIGN KEY (`Language`) REFERENCES `languages` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
