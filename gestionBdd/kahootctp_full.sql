-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 30 nov. 2020 à 18:58
-- Version du serveur :  10.4.16-MariaDB
-- Version de PHP : 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `kahootctp`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `idCATEGORIE` int(11) NOT NULL,
  `texteCATEGORIE` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`idCATEGORIE`, `texteCATEGORIE`) VALUES
(1, 'Tintin (L\'univers du jeune reporter)'),
(2, 'Saints (Comme on connaît les saints, on les fête)'),
(3, 'Histoire de France (Paris vaut bien une messe)'),
(4, 'Jeux de société (La stratégie est au rendez-vous)'),
(5, 'Les Visiteurs (Okay c\'est okay !)'),
(6, 'Pommes (Pour un corps en bonne santé)');

-- --------------------------------------------------------

--
-- Structure de la table `joueur`
--

CREATE TABLE `joueur` (
  `idJOUEUR` int(11) NOT NULL,
  `login` varchar(45) DEFAULT NULL,
  `mdp` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `joueur`
--

INSERT INTO `joueur` (`idJOUEUR`, `login`, `mdp`) VALUES
(1, 'Gautier', 'test'),
(2, 'Mariette', 'test'),
(3, 'Gautier2', 'test'),
(4, 'test', 'test'),
(5, 'invite', 'test');

-- --------------------------------------------------------

--
-- Structure de la table `partie`
--

CREATE TABLE `partie` (
  `ID_PARTIE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `partie`
--

INSERT INTO `partie` (`ID_PARTIE`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14),
(15),
(16),
(17),
(18),
(19),
(20),
(21),
(22),
(23),
(24),
(25);

-- --------------------------------------------------------

--
-- Structure de la table `partie_has_joueur`
--

CREATE TABLE `partie_has_joueur` (
  `ID_PARTI` int(11) NOT NULL,
  `ID_JOUEUR` int(11) NOT NULL,
  `score_partie` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `partie_has_joueur`
--

INSERT INTO `partie_has_joueur` (`ID_PARTI`, `ID_JOUEUR`, `score_partie`) VALUES
(1, 1, 8),
(1, 2, 0),
(2, 2, 0),
(2, 3, 0),
(3, 2, 0),
(3, 3, 0),
(4, 2, 0),
(4, 3, 0),
(5, 2, 0),
(5, 3, 0),
(6, 2, 0),
(6, 3, 0),
(7, 2, 0),
(7, 4, 0),
(8, 2, 0),
(8, 3, 0),
(9, 1, 0),
(9, 2, 0),
(10, 1, 0),
(10, 2, 4),
(11, 2, 0),
(12, 2, 0),
(12, 5, 0),
(14, 1, 0),
(14, 2, 0),
(15, 1, 0),
(15, 2, 0),
(16, 1, 0),
(16, 2, 0),
(17, 1, 0),
(17, 2, 0),
(19, 1, 0),
(19, 2, 0),
(22, 1, 4),
(22, 2, 4),
(23, 1, 0),
(23, 2, 0),
(24, 1, 0),
(24, 2, 0),
(25, 2, 0);

-- --------------------------------------------------------

--
-- Structure de la table `propositions`
--

CREATE TABLE `propositions` (
  `ID_QUESTION` int(11) NOT NULL,
  `ID_REPONSE` int(11) NOT NULL,
  `bonneReponse` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `propositions`
--

INSERT INTO `propositions` (`ID_QUESTION`, `ID_REPONSE`, `bonneReponse`) VALUES
(1, 1, 1),
(1, 2, 0),
(1, 3, 0),
(1, 4, 0),
(2, 6, 0),
(2, 7, 0),
(2, 8, 0),
(2, 5, 1),
(3, 10, 0),
(3, 11, 0),
(3, 9, 1),
(3, 12, 0),
(4, 14, 0),
(4, 13, 1),
(4, 15, 0),
(4, 16, 0),
(5, 12, 0),
(5, 17, 0),
(5, 10, 1),
(5, 9, 0),
(6, 19, 0),
(6, 20, 0),
(6, 18, 1),
(6, 21, 0),
(7, 22, 1),
(7, 23, 0),
(7, 24, 0),
(7, 25, 0),
(8, 4, 0),
(8, 1, 0),
(8, 3, 0),
(8, 26, 1),
(9, 10, 0),
(9, 12, 0),
(9, 11, 0),
(9, 9, 1),
(10, 27, 0),
(10, 2, 1),
(10, 3, 0),
(10, 1, 0),
(11, 29, 0),
(11, 30, 0),
(11, 28, 1),
(11, 31, 0),
(12, 32, 1),
(12, 33, 0),
(12, 34, 0),
(12, 35, 0),
(13, 37, 0),
(13, 31, 0),
(13, 38, 0),
(13, 36, 1),
(14, 40, 0),
(14, 41, 0),
(14, 42, 0),
(14, 39, 1),
(15, 44, 0),
(15, 45, 0),
(15, 43, 1),
(15, 46, 0),
(16, 48, 0),
(16, 49, 0),
(16, 47, 1),
(16, 50, 0),
(17, 52, 0),
(17, 51, 1),
(17, 53, 0),
(17, 54, 0),
(18, 55, 1),
(18, 56, 0),
(18, 22, 0),
(18, 57, 0),
(19, 59, 0),
(19, 53, 0),
(19, 58, 1),
(19, 51, 0),
(20, 61, 0),
(20, 62, 0),
(20, 63, 0),
(20, 60, 1),
(21, 65, 0),
(21, 64, 1),
(21, 66, 0),
(21, 67, 0),
(22, 69, 0),
(22, 68, 1),
(22, 70, 0),
(22, 71, 0),
(23, 73, 0),
(23, 74, 0),
(23, 75, 0),
(23, 72, 1),
(24, 77, 0),
(24, 78, 0),
(24, 79, 0),
(24, 76, 1),
(25, 66, 0),
(25, 80, 1),
(25, 81, 0),
(25, 82, 0),
(26, 84, 0),
(26, 83, 1),
(26, 85, 0),
(26, 86, 0),
(27, 88, 0),
(27, 87, 1),
(27, 89, 0),
(27, 90, 0),
(28, 92, 0),
(28, 91, 1),
(28, 93, 0),
(28, 94, 0),
(29, 95, 1),
(29, 96, 0),
(29, 97, 0),
(29, 98, 0),
(30, 100, 0),
(30, 101, 0),
(30, 99, 1),
(30, 102, 0),
(31, 103, 1),
(31, 104, 0),
(31, 105, 0),
(31, 106, 0),
(32, 108, 0),
(32, 109, 0),
(32, 110, 0),
(32, 107, 1),
(33, 111, 1),
(33, 112, 0),
(33, 113, 0),
(33, 114, 0),
(34, 115, 1),
(34, 116, 0),
(34, 117, 0),
(34, 118, 0),
(35, 119, 1),
(35, 120, 0),
(35, 121, 0),
(35, 122, 0),
(36, 123, 1),
(36, 124, 0),
(36, 125, 0),
(36, 126, 0),
(37, 127, 1),
(37, 128, 0),
(37, 129, 0),
(37, 130, 0),
(38, 132, 0),
(38, 133, 0),
(38, 131, 1),
(38, 134, 0),
(39, 136, 0),
(39, 135, 1),
(39, 137, 0),
(39, 138, 0),
(40, 140, 0),
(40, 139, 1),
(40, 141, 0),
(40, 142, 0),
(41, 143, 1),
(41, 144, 0),
(41, 145, 0),
(41, 146, 0),
(42, 148, 0),
(42, 147, 1),
(42, 149, 0),
(42, 150, 0),
(43, 152, 0),
(43, 153, 0),
(43, 151, 1),
(43, 154, 0),
(44, 155, 1),
(44, 156, 0),
(44, 157, 0),
(44, 158, 0),
(45, 160, 0),
(45, 161, 0),
(45, 162, 0),
(45, 159, 1),
(46, 163, 1),
(46, 164, 0),
(46, 165, 0),
(46, 166, 0),
(47, 167, 1),
(47, 168, 0),
(47, 169, 0),
(47, 170, 0),
(48, 172, 0),
(48, 171, 1),
(48, 173, 0),
(48, 174, 0),
(49, 175, 1),
(49, 147, 0),
(49, 176, 0),
(49, 177, 0),
(50, 179, 0),
(50, 180, 0),
(50, 178, 1),
(50, 181, 0),
(51, 183, 0),
(51, 184, 0),
(51, 185, 0),
(51, 182, 1),
(52, 187, 0),
(52, 188, 0),
(52, 189, 0),
(52, 186, 1),
(53, 190, 1),
(53, 191, 0),
(53, 192, 0),
(53, 193, 0),
(54, 195, 0),
(54, 196, 0),
(54, 197, 0),
(54, 194, 1),
(55, 199, 0),
(55, 200, 0),
(55, 201, 0),
(55, 198, 1),
(56, 203, 0),
(56, 204, 0),
(56, 202, 1),
(56, 205, 0),
(57, 207, 0),
(57, 208, 0),
(57, 209, 0),
(57, 206, 1),
(58, 211, 0),
(58, 210, 1),
(58, 212, 0),
(58, 213, 0),
(59, 215, 0),
(59, 216, 0),
(59, 217, 0),
(59, 214, 1),
(60, 219, 0),
(60, 220, 0),
(60, 221, 0),
(60, 218, 1);

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

CREATE TABLE `question` (
  `ID_QUESTION` int(11) NOT NULL,
  `texteQUESTION` varchar(1000) DEFAULT NULL,
  `ID_CATEGORIE` int(11) NOT NULL,
  `ID_BONNE_REPONSE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `question`
--

INSERT INTO `question` (`ID_QUESTION`, `texteQUESTION`, `ID_CATEGORIE`, `ID_BONNE_REPONSE`) VALUES
(1, 'Dans quelle aventure Tintin se retrouve-t-il face à un impressionnant Yeti ?', 1, 1),
(2, 'Quel est le juron préféré du capitaine Haddock, meilleur ami de Tintin ?', 1, 5),
(3, 'De qui est amoureuse la cantatrice italienne Bianca Castafiore ?', 1, 9),
(4, 'Dans Tintin, quelle est la différence visuelle entre Dupond et Dupont ?', 1, 13),
(5, 'Quel personnage est en lévitation sur la couverture des « Sept Boules de cristal » ?', 1, 10),
(6, 'Dans quelle ville Tintin se fait-il enlever dans « Tintin en Amérique » ?', 1, 18),
(7, 'Qui a volé les Bijoux de la Castafiore, dont une émeraude de grande valeur ?', 1, 22),
(8, 'Quelle est la suite de l\'album « Les Sept Boules de cristal » ?', 1, 26),
(9, 'De qui le pirate Rackham le Rouge est-il un lointain ancêtre ?', 1, 9),
(10, 'Dans quel album Tintin se rend-il dans une colonie belge ?', 1, 2),
(11, 'Quelle sainte est la mère de la Vierge Marie, connue comme étant la Mère de Dieu ?', 2, 28),
(12, 'Quelle sainte est la patronne des musiciens ainsi que des brodeuses ?', 2, 32),
(13, 'Quelle sainte lava les pieds de Jésus au cours du banquet chez Simon ?', 2, 36),
(14, 'Quel est le jour de la Saint-Valentin, devenu une fête laïque au XXe siècle ?', 2, 39),
(15, 'Par qui a été perpétré le massacre de la Saint-Barthélémy le 24 août 1572 ?', 2, 43),
(16, 'Quel saint ne faut-il pas trop honorer quand on surveille sa ligne ?', 2, 47),
(17, 'Quel saint garde les clefs du salut des âmes et du Paradis ?', 2, 51),
(18, 'Sur de nombreux tableaux de maîtres, par quel oiseau le Saint-Esprit est-il symbolisé ?', 2, 55),
(19, 'Quel saint, considéré comme le patron des voyageurs, a porté le Christ sur son dos ?', 2, 58),
(20, 'Durant quel mois ont lieu les saints de glace, célébrés les 11, 12 et 13 du mois ?', 2, 60),
(21, 'Quelle ville française était appelée la cité des papes au Moyen Âge ?', 3, 64),
(22, 'Quel diplomate français a succédé au Cardinal de Richelieu ?', 3, 68),
(23, 'Quelle reine de France a porté le chapeau dans l\'affaire du collier ?', 3, 72),
(24, 'Quand François Mitterrand fut-il élu président de la France pour la première fois ?', 3, 76),
(25, 'Quelle ville Jeanne d\'Arc a-t-elle héroïquement libérée des Anglais ?', 3, 80),
(26, 'Quel homme d\'État français fut surnommé le Tigre ou encore le Père la victoire ?', 3, 83),
(27, 'Quelle héroïne de l\'histoire de France était la Pucelle d\'Orléans ?', 3, 87),
(28, 'Sur quelle principauté a régné la maison de Matignon ?', 3, 91),
(29, 'Quel journal a brillé en sortant le premier l\'affaire des diamants de Bokassa ?', 3, 95),
(30, 'Quel Guillaume, duc de Normandie, parfois surnommé le Bâtard, a conquis l\'Angleterre ?', 3, 99),
(31, 'Au « Trivial Pursuit », que faut-il faire quand on a obtenu tous les camemberts ?', 4, 103),
(32, 'Quel jeu créé en 1985 par Rob Angel consiste à faire deviner un mot en le dessinant ?', 4, 107),
(33, 'Quel jeu de société consiste à ruiner ses adversaires par des opérations immobilières ?', 4, 111),
(34, 'Au « Scrabble », le joueur qui démarre la partie place un mot sur le plateau qui compte...', 4, 115),
(35, 'Quel jeu d\'adresse, praticable de 2 à 6 joueurs, consiste à se saisir de baguettes une par une ?', 4, 119),
(36, 'Quelle est la pièce la plus mobile aux échecs parmi les seize pièces de chaque joueur ?', 4, 123),
(37, 'Dans quel jeu populaire faut-il reconstituer une seule combinaison gagnante avec trois dés ?', 4, 127),
(38, 'Que faut-il aligner pour remporter une partie de « Puissance 4 », jeu de société dérivé du Morpion ?', 4, 131),
(39, 'De combien de cartes est généralement constitué un jeu de bataille ?', 4, 135),
(40, 'Au poker, parmi les combinaisons possibles, quelle est la suite de cartes la plus puissante ?', 4, 139),
(41, 'Quel personnage est incarné par Jean Reno dans la saga « Les Visiteurs » ?', 5, 143),
(42, 'Qui incarne Béatrice de Montmirail dans la comédie « Les Visiteurs » ?', 5, 147),
(43, 'Qui est Ginette Sarclay, excentrique personnage du film « Les Visiteurs » ?', 5, 151),
(44, 'Comment Jacquouille appelle-t-il le postier dans le film « Les Visiteurs » ?', 5, 155),
(45, 'Où Jacquouille rencontre-t-il Ginette dans « Les Visiteurs » ?', 5, 159),
(46, 'Quelle est la réplique mythique de Jacquouille dans le film « Les Visiteurs » ?', 5, 163),
(47, 'Dans « Les Visiteurs », un des personnages se nomme Jacquouille la...', 5, 167),
(48, 'Où se dresse le château de Godefroy le Hardi dans le film « Les Visiteurs » ?', 5, 171),
(49, 'Qui interprète Frénégonde de Pouille dans « Les Visiteurs 2 » ?', 5, 175),
(50, 'De qui Jacquouille tombe-t-il amoureux dans « Les Visiteurs » ?', 5, 178),
(51, 'Dans quel verger poussent presque exclusivement des pommiers ?', 6, 182),
(52, 'Quelle est la variété de pommes la plus répandue en France ?', 6, 186),
(53, 'De quelle vitamine la pomme est-elle la mieux pourvue ?', 6, 190),
(54, 'Quelle partie de la pomme contient le plus de vitamine ?', 6, 194),
(55, 'Que permet de réguler le gel formé par la pectine présente dans la pomme ?', 6, 198),
(56, 'Complétez ce diction : « Une pomme chaque matin éloigne... »', 6, 202),
(57, 'Quelle confiserie est constituée d\'une pomme fraîche entourée de sucre cuit, souvent rouge ?', 6, 206),
(58, 'Dans quel dessert les pommes sont-elles enrobées de pâte et frites dans l\'huile ?', 6, 210),
(59, 'Quelle grande entreprise multinationale américaine a pour logo une pomme ?', 6, 214),
(60, 'Quelle célèbre princesse originaire de contes a été empoisonnée par une pomme ?', 6, 218);

-- --------------------------------------------------------

--
-- Structure de la table `question_has_a_partie`
--

CREATE TABLE `question_has_a_partie` (
  `ID_PARTIE` int(11) NOT NULL,
  `ID_QUESTION` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `question_has_a_partie`
--

INSERT INTO `question_has_a_partie` (`ID_PARTIE`, `ID_QUESTION`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 10),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 9),
(4, 10),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),
(5, 6),
(5, 7),
(5, 8),
(5, 9),
(5, 10),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(6, 7),
(6, 8),
(6, 9),
(6, 10),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 7),
(7, 8),
(7, 9),
(7, 10),
(8, 1),
(8, 2),
(8, 3),
(8, 4),
(8, 5),
(8, 6),
(8, 7),
(8, 8),
(8, 9),
(8, 10),
(9, 31),
(9, 32),
(9, 33),
(9, 34),
(9, 35),
(9, 36),
(9, 37),
(9, 38),
(9, 39),
(9, 40),
(10, 1),
(10, 2),
(10, 3),
(10, 4),
(10, 5),
(10, 6),
(10, 7),
(10, 8),
(10, 9),
(10, 10),
(12, 41),
(12, 42),
(12, 43),
(12, 44),
(12, 45),
(12, 46),
(12, 47),
(12, 48),
(12, 49),
(12, 50),
(14, 51),
(14, 52),
(14, 53),
(14, 54),
(14, 55),
(14, 56),
(14, 57),
(14, 58),
(14, 59),
(14, 60),
(15, 31),
(15, 32),
(15, 33),
(15, 34),
(15, 35),
(15, 36),
(15, 37),
(15, 38),
(15, 39),
(15, 40),
(16, 51),
(16, 52),
(16, 53),
(16, 54),
(16, 55),
(16, 56),
(16, 57),
(16, 58),
(16, 59),
(16, 60),
(17, 21),
(17, 22),
(17, 23),
(17, 24),
(17, 25),
(17, 26),
(17, 27),
(17, 28),
(17, 29),
(17, 30),
(19, 31),
(19, 32),
(19, 33),
(19, 34),
(19, 35),
(19, 36),
(19, 37),
(19, 38),
(19, 39),
(19, 40),
(22, 21),
(22, 22),
(22, 23),
(22, 24),
(22, 25),
(22, 26),
(22, 27),
(22, 28),
(22, 29),
(22, 30),
(23, 51),
(23, 52),
(23, 53),
(23, 54),
(23, 55),
(23, 56),
(23, 57),
(23, 58),
(23, 59),
(23, 60),
(24, 41),
(24, 42),
(24, 43),
(24, 44),
(24, 45),
(24, 46),
(24, 47),
(24, 48),
(24, 49),
(24, 50),
(25, 11),
(25, 12),
(25, 13),
(25, 14),
(25, 15),
(25, 16),
(25, 17),
(25, 18),
(25, 19),
(25, 20);

-- --------------------------------------------------------

--
-- Structure de la table `reponse`
--

CREATE TABLE `reponse` (
  `ID_REPONSE` int(11) NOT NULL,
  `texteREPONSE` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `reponse`
--

INSERT INTO `reponse` (`ID_REPONSE`, `texteREPONSE`) VALUES
(1, 'Tintin au Tibet'),
(2, 'Tintin au Congo'),
(3, 'Coke en Stock'),
(4, 'Le Lotus bleu'),
(5, 'Mille sabords'),
(6, 'Tonnerre du diable'),
(7, 'Nom de Zeus'),
(8, 'Boursicot'),
(9, 'Haddock'),
(10, 'Tournesol'),
(11, 'Nestor'),
(12, 'Tintin'),
(13, 'La moustache'),
(14, 'Le costume'),
(15, 'Le nez'),
(16, 'Le chapeau'),
(17, 'Milou'),
(18, 'Chicago'),
(19, 'New York'),
(20, 'Texas'),
(21, 'Detroit'),
(22, 'Une pie'),
(23, 'Tryphon Tournesol'),
(24, 'Les bohémiens'),
(25, 'Les frères Loiseau'),
(26, 'Le Temple du Soleil'),
(27, 'Tintin en Amérique'),
(28, 'Anne'),
(29, 'Odile'),
(30, 'Jeanne'),
(31, 'Marthe'),
(32, 'Sainte Cécile'),
(33, 'Sainte Véronique'),
(34, 'Sainte Agathe'),
(35, 'Sainte Marguerite'),
(36, 'Madeleine'),
(37, 'Béthanie'),
(38, 'Béatrice'),
(39, 'Le 14 février'),
(40, 'Le 16 février'),
(41, 'Le 18 février'),
(42, 'Le 12 février'),
(43, 'Les Catholiques'),
(44, 'Les Anglicans'),
(45, 'Les Protestants'),
(46, 'Les Chouans'),
(47, 'Saint-honoré'),
(48, 'Saint-germain'),
(49, 'Saint-charles'),
(50, 'Saint-pierre'),
(51, 'Saint Pierre'),
(52, 'Saint Jean'),
(53, 'Saint Luc'),
(54, 'Saint Thomas'),
(55, 'Une colombe'),
(56, 'Une grue'),
(57, 'Un merle'),
(58, 'Saint Christophe'),
(59, 'Saint Marc'),
(60, 'Le mois de mai'),
(61, 'Le mois de juin'),
(62, 'Le mois de mars'),
(63, 'Le mois de février'),
(64, 'Avignon'),
(65, 'Metz'),
(66, 'Tours'),
(67, 'Nantes'),
(68, 'Jules Mazarin'),
(69, 'Philippe Mancini'),
(70, 'Charles de Mantoue'),
(71, 'Antonio Barberini'),
(72, 'Marie-Antoinette'),
(73, 'Marie-Louise'),
(74, 'Marie-Joséphine'),
(75, 'Marie-Thérèse'),
(76, '1981'),
(77, '1991'),
(78, '1976'),
(79, '1986'),
(80, 'Orléans'),
(81, 'Angers'),
(82, 'Le Mans'),
(83, 'Clémenceau'),
(84, 'Poincaré'),
(85, 'Joffre'),
(86, 'Foch'),
(87, 'Jeanne d\'Arc'),
(88, 'Alessandra Scala'),
(89, 'Hélène Duglioli'),
(90, 'Rita de Cascia'),
(91, 'Monaco'),
(92, 'Condé'),
(93, 'Foucarmont'),
(94, 'Cambrai'),
(95, 'Canard Enchaîné'),
(96, 'Charlie Hebdo'),
(97, 'Minute'),
(98, 'Paris Match'),
(99, 'Le Conquérant'),
(100, 'Le Pieux'),
(101, 'Le Hardi'),
(102, 'Le Chevelu'),
(103, 'Rejoindre le centre'),
(104, 'Mimer une carte'),
(105, 'Tous les perdre'),
(106, 'Compter ses points'),
(107, 'Pictionary'),
(108, 'Mastermind'),
(109, 'Uno'),
(110, 'Blokus'),
(111, 'Monopoly'),
(112, '1000 bornes'),
(113, 'Boggle'),
(114, 'La bonne paye'),
(115, 'Double'),
(116, 'Pour 100 points'),
(117, 'Triple'),
(118, 'Pour 50 points'),
(119, 'Le Mikado'),
(120, 'Le Mandarin'),
(121, 'Le Mahjong'),
(122, 'Le Samouraï'),
(123, 'La reine'),
(124, 'Le roi'),
(125, 'Le cavalier'),
(126, 'Le fou'),
(127, '421'),
(128, 'Craps'),
(129, 'Yahtzee'),
(130, 'Dudo'),
(131, 'Des jetons'),
(132, 'Des dés'),
(133, 'Des billes'),
(134, 'Des cartes'),
(135, '52'),
(136, '43'),
(137, '65'),
(138, '36'),
(139, 'La quinte flush'),
(140, 'La paire'),
(141, 'Le brelan'),
(142, 'Le carré'),
(143, 'Godefroy'),
(144, 'Thibaud'),
(145, 'Jacquouille'),
(146, 'Florian'),
(147, 'Valérie Lemercier'),
(148, 'Murielle Robin'),
(149, 'Isabelle Nanty'),
(150, 'Michèle Laroque'),
(151, 'Une SDF'),
(152, 'Une servante'),
(153, 'Une comtesse'),
(154, 'Une infirmière'),
(155, 'Le sarrazin'),
(156, 'Le noir'),
(157, 'Le vilain'),
(158, 'Le gueux'),
(159, 'Sur un parking'),
(160, 'Dans un restaurant'),
(161, 'Au supermarché'),
(162, 'En discothèque'),
(163, 'Okay'),
(164, 'Oui messire'),
(165, 'D\'accord'),
(166, 'Voui'),
(167, 'Fripouille'),
(168, 'Trouille'),
(169, 'Grenouille'),
(170, 'Gargouille'),
(171, 'Montmirail'),
(172, 'Montargis'),
(173, 'Mont-Dore'),
(174, 'Mont-de-Marsan'),
(175, 'Muriel Robin'),
(176, 'Marie-Anne Chazel'),
(177, 'Josiane Balasko'),
(178, 'Dame Ginette'),
(179, 'Dame Josette'),
(180, 'Dame Janette'),
(181, 'Dame Gilberte'),
(182, 'Pommeraie'),
(183, 'Vergepommes'),
(184, 'Vergeture'),
(185, 'Pommiseau'),
(186, 'Golden'),
(187, 'Granny Smith'),
(188, 'Jonagold'),
(189, 'Gala'),
(190, 'Vitamine C'),
(191, 'Vitamine E'),
(192, 'Vitamine B'),
(193, 'Vitamine D'),
(194, 'La peau'),
(195, 'La pulpe'),
(196, 'Le pédoncule'),
(197, 'Les pépins'),
(198, 'Le cholestérol'),
(199, 'Le campestérol'),
(200, 'Le lanostérol'),
(201, 'Le phytostérol'),
(202, 'Le médecin'),
(203, 'Le pantin'),
(204, 'Le chagrin'),
(205, 'Le destin'),
(206, 'Pomme d\'amour'),
(207, 'Le crumble'),
(208, 'Apfelstrudel'),
(209, 'Tarte Tatin'),
(210, 'Un beignet'),
(211, 'Un flan'),
(212, 'Une bûche'),
(213, 'Une croûte'),
(214, 'Apple'),
(215, 'IBM'),
(216, 'Microsoft'),
(217, 'Xerox'),
(218, 'Blanche-Neige'),
(219, 'Jasmine'),
(220, 'Cendrillon'),
(221, 'Aurore');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`idCATEGORIE`),
  ADD UNIQUE KEY `idCategorie` (`idCATEGORIE`);

--
-- Index pour la table `joueur`
--
ALTER TABLE `joueur`
  ADD PRIMARY KEY (`idJOUEUR`),
  ADD UNIQUE KEY `login` (`login`);

--
-- Index pour la table `partie`
--
ALTER TABLE `partie`
  ADD PRIMARY KEY (`ID_PARTIE`);

--
-- Index pour la table `partie_has_joueur`
--
ALTER TABLE `partie_has_joueur`
  ADD PRIMARY KEY (`ID_PARTI`,`ID_JOUEUR`),
  ADD KEY `ID_JOUEUR` (`ID_JOUEUR`);

--
-- Index pour la table `propositions`
--
ALTER TABLE `propositions`
  ADD KEY `fk_QUESTION_has_REPONSE_REPONSE1_idx` (`ID_REPONSE`),
  ADD KEY `fk_QUESTION_has_REPONSE_QUESTION1_idx` (`ID_QUESTION`);

--
-- Index pour la table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`ID_QUESTION`),
  ADD KEY `fk_QUESTION_REPONSE1_idx` (`ID_BONNE_REPONSE`),
  ADD KEY `fk_QUESTION_CATEGORIE1_idx` (`ID_CATEGORIE`);

--
-- Index pour la table `question_has_a_partie`
--
ALTER TABLE `question_has_a_partie`
  ADD PRIMARY KEY (`ID_PARTIE`,`ID_QUESTION`),
  ADD KEY `ID_QUESTION` (`ID_QUESTION`);

--
-- Index pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD PRIMARY KEY (`ID_REPONSE`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `idCATEGORIE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `joueur`
--
ALTER TABLE `joueur`
  MODIFY `idJOUEUR` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `partie`
--
ALTER TABLE `partie`
  MODIFY `ID_PARTIE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pour la table `question`
--
ALTER TABLE `question`
  MODIFY `ID_QUESTION` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `ID_REPONSE` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=222;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `partie_has_joueur`
--
ALTER TABLE `partie_has_joueur`
  ADD CONSTRAINT `fk_Partie_Patie_has_joueur` FOREIGN KEY (`ID_PARTI`) REFERENCES `partie` (`ID_PARTIE`),
  ADD CONSTRAINT `partie_has_joueur_ibfk_1` FOREIGN KEY (`ID_JOUEUR`) REFERENCES `joueur` (`idJOUEUR`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `propositions`
--
ALTER TABLE `propositions`
  ADD CONSTRAINT `FK_reponse` FOREIGN KEY (`ID_REPONSE`) REFERENCES `reponse` (`ID_REPONSE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `propositions_ibfk_1` FOREIGN KEY (`ID_QUESTION`) REFERENCES `question` (`ID_QUESTION`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FK_idCat_Question` FOREIGN KEY (`ID_CATEGORIE`) REFERENCES `categorie` (`idCATEGORIE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `question_fk_bonnerep` FOREIGN KEY (`ID_BONNE_REPONSE`) REFERENCES `reponse` (`ID_REPONSE`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `question_has_a_partie`
--
ALTER TABLE `question_has_a_partie`
  ADD CONSTRAINT `question_has_a_partie_ibfk_1` FOREIGN KEY (`ID_PARTIE`) REFERENCES `partie` (`ID_PARTIE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `question_has_a_partie_ibfk_2` FOREIGN KEY (`ID_QUESTION`) REFERENCES `question` (`ID_QUESTION`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
