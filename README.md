# Projet DASI - Partie 2 - GustatIF

## Presentation
  Ceci est un projet fait enDASI 3IF à l'INSA de Lyon.
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

### IntelliJ IDEA

Sous IntelliJ, ouvrir le projet INSA-DASI-GustatIF. 

Maven a du s'initialiser et vous pouvez mettre à jour les dépendances.
Ensuite il faut ajouter le module `GustatIF` à `WebApp` en tant que dépendance. 
Pour cela :

> File > Project Structure > Modules > WebApp >
 Onglet Dependencies > Add > Module Dependency > GustatIF

A partir de là, le projet devrait ce compiler correctement.


Maintenant il ne reste plus qu'à lancer l'application, en deux temps :
* Lancer le serveur Derby (Windows) : ```"C:\Program Files\Java\jdk1.8.0_77\db\bin\startNetworkServer" -noSecurityManager```
* Déployer l'application sur Tomcat : Bouton start sur IntelliJ

Après l'application se déploie sur Tomcat. Pour mettre à jour le serveur Web,
 il suffit de redéployer le serveur (bouton déployer).
 
N.B. : Tomcat est lancé automatiquement par IntelliJ.
 
 