# 🏰 
# projet2-ps5-21-22-ps5-21-22-projet2-b : Projet Citadelles

# Description du projet 
* Simulation d'une partie du jeu de société original Citadelles
* Les parties se jouent automatiquement : 3 à 7 robots s'affrontent
* Plusieurs stratégies de jeux sont appliquées par des robots

# Comment lancer les main ? 

* Lancer les 1000 parties avec affichage des statistiques 
```bash
mvn clean package
mvn exec:java
```
ou
```bash
mvn clean package
mvn exec:java@stats
``` 


* Lancer une partie unique : 
```bash
mvn clean package
mvn exec:java@oneGame
```


# Ce qui a été fait avant le rush final
##  Ce qui a été réalisé


### Les cartes : 
* ✅ Les cartes quartier sont implémentées totalement. (cout de construction, couleur)
* ✅ Les cartes merveilles du jeu de base sont implémentés ainsi que leur pouvoir.
* ✅ L’action de la carte couronne est intégrée au jeu (mais pas sous forme de carte) : le roi du tour précédent est le premier à choisir.  
* ✅ Le paquet de cartes contient toutes les cartes du jeu de base 
* ✅ Impossibilité de construire 2 fois la même carte 


### Le déroulement d’un tour 
* Choix du rôle selon le rôle du tour précédent 
* On joue seulement si on n'a pas été tué
* Choix entre une carte ou prendre 2 pièces 
* Recevoir les taxes avant ou après avoir construit 
* Construire un quartier 
* Appel au pouvoir du personnage (s’il en a un)
* Utilisation des cartes merveilles s’il en a


### Fin du jeu 
#### Des points bonus sont attribués
* ✅ pour le premier à avoir gagné (+4 pour celui qui gagne, +2 pour ceux qui gagnent au même tour qu’au gagnant)
* ✅ pour n'importe quel joueur ayant construit les 5 couleurs de cartes (Rouge, Vert, Bleu, Jaune, Violet) 
* ✅ pour les joueurs ayant construit les merveilles Université et Dracoport
#### Les joueurs sont triés en fonction de leur nombre de points
Le premier de ce tri est le gagnant du jeu


### Personnages : 
Tous les personnages ont été implémentés ainsi que leurs pouvoir soit :
1. **L’assassin** (pouvoir : assassine un autre personnage)
2. **Le voleur** (pouvoir : vole un autre personnage)
3. **Le magicien** (pouvoir : échange ses cartes avec un joueur ou échange des cartes de sa main avec la pioche)
4. **Le roi** (pouvoir : ses quartiers nobles rapportent et il devient automatiquement le premier joueur)
5. **L’évêque** (pouvoir : ses quartiers religieux rapportent il est protégé contre le Condottière
6. **Le marchand** (pouvoir : ses quartiers marchands rapportent il gagne une pièce d'or)
7. **L’architecte** (pouvoir : il pioche deux cartes en plus et peut bâtir jusqu'à trois quartiers)
8. **Le condottiere** (pouvoir : ses quartiers militaires rapportent il peut détruire un quartier)


### Les stratégies
* Une stratégie qui choisit des personnages, de piocher des pièces ou des cartes et de construire ou non des quartiers de manière aléatoire.
* Une stratégie qui construit un maximum de quartiers les moins chers possibles et qui choisit le personnage marchand en premier sinon il prend un personnage avec la couleur plus fréquente parmi ses quartiers construits. 


## Ce qui n’a pas été réalisé  
* ❌ Le cas d’égalité des joueurs n’est pas géré, affichage des points en fin de partie sans classement.
* ❌ Cartes écartées face visible au moment du choix des personnages en début de tour : peu importe le nombre de joueurs, le 1er joueur qui choisit aura toujours un choix parmi les 8 cartes, le suivant parmi les 7 restantes, etc. 


# Fonctionnalité 1

Avant le rush final, le jeu affichait toutes les informations d’une sortie grâce à une classe d’entrée sortie nommée (IO). Seul le moteur de jeu (GameEngine) a le droit de faire appel à cette classe, cela permet d’avoir un code plus simple. Pour les logs, nous avons décidé d’étendre cette classe, en redéfinissant sa façon d’afficher et en ajoutant la lecture et écriture des fichiers CSV. 


Nous avons expérimenté rapidement l’utilisation d’un Logger mais dans les contraintes de temps MVP nous avons préféré garder notre code testé et fonctionnel avec nos `System.out.println()`. L’avantage étant qu’il est possible d’afficher ou non l'entièreté des informations d’une partie pour chacune de nos classes `main`, simplement en ajoutant un argument de ligne de commande comme avec l’utilisation d’un Logger ou non. (main “oneGame” sans argument affiche tout et main “stats” avec argument affiche toutes les 1000 parties) 


# Fonctionnalité 2 

pour cette fonctionnalité nous avons fait ce qui a été demandé : 
* Affichage du nom du bot avec sa proportion de parties gagnées et son nombre de points moyen sur le format suivant : `<BotName> has won <#Win> games out of <#ofGames>, average: <#ofAveragePoints> points`

* les données brutes sont enregistrées au format CSV dans le dossier `save/results.csv`, le fichier contient dans l’ordre suivant : 
pour chaque partie :
`nom du bot, victoire(0 : non : 1 oui), nombre de points, nombre de points bonus, nombre de pièces, prix moyen des quartiers construits, nombre de cartes rouges construites, nombre de cartes vertes construites, nombre de cartes bleues construites, nombre de cartes jaunes construites, nombre de cartes grises construites, nombre de cartes violettes construites` 

* les statistiques supplémentaires sont enregistrées à côté du fichier `results.csv`, dans le fichier `resultsComputed.txt` 



Les informations de fin de partie sont d’abord ajoutées ligne par ligne dans le fichier `results.csv` qui peut être vide ou déjà rempli avec des statistiques. Ce fichier est enfin lu est calculé afin d’afficher dans la console les informations nombre de victoires sur nombre de parties et score moyen, les statistiques supplémentaires sont enregistrées dans  `resultsComputed.txt`. 


# Fonctionnalité 3

Les fonctionnalités du super robot basé sur les commentaires de Richard et d’Alphonse ont été réalisées, le robot fait donc des choix en prenant en compte les autres joueurs contrairement à nos autres stratégies. Nous avons déterminé plusieurs cas à travailler en se basant sur le commentaire de Richard. Ce dernier nous apprend qu’il faut pouvoir contrer son adversaire lorsque celui-ci s’apprête à gagner. Plusieurs cas sont déterminants pour gagner et tous sont liés au choix du personnage. Nous avons ainsi travaillé sur la manière de choisir le personnage en prenant en compte l’état du jeu et de nos adversaires. Évidemment, le choix du personnage amène un pouvoir à utiliser de telle façon à contrer son adversaire.


Pour concevoir le super robot, nous avons divisé les stratégies de choix de personnage et de construction de quartiers en deux. Cela nous permet d’avoir à la fois une stratégie pour contrer un joueur (Richard), mais aussi de pouvoir avoir une stratégie de construction d’un maximum de quartier (bâtisseur d’Alphonse).


Au niveau des statistiques, le robot qui adopte la stratégie de construire le plus possible gagne en majorité sur les mille parties, car il ne joue pas ce à quoi le super robot s'attend. Le super robot essaye de stopper sa progression en jouant agressivement, mais l’autre robot jouant de manière isolé, le super robot n’arrive pas à le bloquer. On a donc d’un côté un robot qui construit coûte que coûte et de l’autre un robot qui s’arrête de construire pour bloquer un adversaire sur le point de gagner. Cependant, toutes ses tentatives sont un échec et entraînent sa défaite.