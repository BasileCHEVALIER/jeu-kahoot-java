# kahoot simple en java 

@Copyright Gautier Masse 30 novembre 2020 La1  
* Gestion de projet & bdd : Mariette Leleu 
* Dev : Gautier Masse 
## Pourquoi ce projet 

## Pour voir notre code amélioré voir groupe 3.

L'objectif est de decouvrir le principe de client-serveur en java avec des theads en java et des sockets.
En réalisant un projet très simpliste de questionnaire avec base de données, import de json.
## Comment utiliser l'application ? 

1. Installer mysql avec la commande sudo apt install mysql-server
2. Importer la base de données dans votre gestionnaire de base de donnée comme phpmyadmin
3. Modifier le fichier requeteKahoot le login et le passwaord pour acceder à votre gestionnaire de base de données 
4. En fonction de votre gestionnaire de base de données, vous devez télècharger le driver qui convient. 
5. Ajouter le driver dans votre librairie de projet. Par exemple sur intellij, vous pouvez cliquer sur File puis Projet Structure puis librairie. 
   
## Comment lancer l'application ? 

* Run la classe serveur 
* Run au moins 2 fois la classe applicationClient pour pouvoir jouer à 2 personnes. 
La partie se lance lorsque au moins 2 personnes sont inscrits ou bien connectés. 
Le code a été conçu pour faire des parties à 2. Si une troisième personne se connecte le serveur va lancer de lui même une nouvelle partie.
* Dans votre ide faire en sorte de pouvoir lancer la classe ApplicationClient en mode "allow parallel run"
## Warning !
* Si il manque des utilisateurs ils vont devoir attendre qu'une personne vienne jouer 

## Les améliorations ? 
* Faire en sorte de pouvoir choisir le nombre de personnes dans la partie lors du lancement du serveur. 
* Ajouter une applicationAdmin pour gérer graphiquement les joueurs et les parties. 
* Corriger l'exception lors d'une fermeture d'une fenêtre client !
* Ajouter une tempo pour envoyer les questions au même moment au client 
