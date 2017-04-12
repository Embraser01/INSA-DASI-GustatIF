# Projet DASI - Partie 2 - GustatIF

## Presentation
  Ceci est un projet fait en DASI 3IF à l'INSA de Lyon.
  Auteurs :
*    Marc-Antoine FERNANDES
*    Lucas ONO
*    Ye YUAN

## Mise en place de l'environnement de développement

### Pré-requis

Avant de pouvoir cloner le projet il faut ceci:

* JDK 8.0+ (la version 7 doit marcher mais pas tester)
* [Tomcat 8.0+](https://tomcat.apache.org/download-80.cgi)


### Cloner le projet

Tout d'abord cloner le repo git depuis Netbeans/IntelliJ/Git


### Netbeans

Sous Netbeans, ouvrir le projet INSA-DASI-GustatIF. 

Selectionner l'application WebApp comme application principale.
A partir de là, le projet devrait pouvoir s'executer correctement.

Ne pas oublier de lancer le serveur Derby depuis l'onglet __Service__ !

N.B. : Si jamais Netbeans n'arrive pas à déployer le fichier __.war__, on peut le faire depuis le manager Tomcat.
    Ce fichier est disponible dans `WebApp/target/`

------------------------------------------------------------

### IntelliJ IDEA

Sous IntelliJ, ouvrir le projet INSA-DASI-GustatIF. 

Le projet est déjà prêt à être lancé ! Cela ce fait en deux temps :
* Lancer le serveur Derby (Windows) : ```"C:\Program Files\Java\jdk1.8.0_77\db\bin\startNetworkServer" -noSecurityManager```
* Déployer l'application sur Tomcat : Bouton start sur IntelliJ

Après l'application se déploie sur Tomcat. Pour mettre à jour le serveur Web,
 il suffit de redéployer le serveur (bouton déployer).
 
N.B. : Tomcat est lancé automatiquement par IntelliJ.
 
 