# Rapport - KX-B

### 1. Comment est faite l'architecture du projet ? Et quels choix vous ont amené à la réaliser ainsi ?

On a choisi de s’inspirer le plus possible de la réalité lorsqu'on a numérisé ce jeu de plateau, en effet il y a un maitre de jeu qui fait toutes les vérifications et les appels aux autres objets.

Le joueur possède des cartes, des pièces et a une stratégie qui peut changer ou non. Enfin, on évite un maximum les chaines de caractère, on privilégie les types énumérés pour éviter les erreurs dans le code.

Le projet est entièrement codé en anglais.

### 2. Où trouver les infos dans la doc ?

Pour la documentation nous avons beaucoup utilisé le site http://jeuxstrategie.free.fr/Citadelles_complet.php , pour toutes les informations du jeu. 

Chaque méthode a un nom explicite ce qui permet une meilleure compréhension de sa fonctionnalité et permet de lire l'ensemble du code plus rapidement.

### 3. Qui est responsable de quoi / qui a fait quoi ?

Nous avons divisé le travail afin de pouvoir tous toucher aux différentes parties du code en passant du moteur de jeu (Game Engine), aux joueurs avec ses stratégies, aux cartes ainsi qu’aux tests unitaires. L’avantage est que toute l’équipe a une vision globale du code qu’elle conçoit, la partie négative est que la répartition du travail nous prend plus de temps.

Pour plus de détail voir : https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-b

### 4. Quel est le process de l'équipe ? (Comment git est utilisé, …)

Nous rédigeons au début de la séance 1 ou 2 Milestone avec les slices correspondantes. Une fois cela fait, chacun part de son côté et réalise les issues qui lui ont été assignées. Nous essayons pour chaque Milestone de diviser le travail équitablement.

D'un point de vue de Git, nous développons chacun sur nos branches respectives afin de s’isoler du code fonctionnel (code de production). Chaque issue GitHub est accompagnée de ses tests unitaires avant d'être déployée sur la branche principale de l'équipe (master). Cela permet d'ajouter des fonctionnalités tout en gardant un jeu jouable du début à la fin. Un « code coverage » de 100% du nouveau code ajouté est aussi requis. Seule la méthode launchgame() du GameEngine n’est pas testée, car elle n’est pas testable unitairement. 

### 5. Avancement sur les fonctionnalités (quelles slices sont faites ? lesquelles restent à faire ?)

*Pour un tour de jeu :*

 - On peut avoir jusqu’à 7 joueurs
 - Le sens de la pioche des personnages est défini en fonction du joueur qui avait pioché le Roi lors du tour précédent
 - Les joueurs choisissent leurs personnages en fonction d’une stratégie
 - Les joueurs sont appelés en fonction de l’ordre de leur personnage

*Pour un tour de joueur :*

 - Utiliser le pouvoir du personnage choisi
 - Utiliser le pouvoir de ses éventuelles cartes merveilles construites
 - Choisir entre des pièces et des cartes
 - Construire un quartier (s’il possède suffisamment de pièces)
 - Récolter des impôts si possible

*Pour une fin de partie :*

- Si un des joueurs a construit les 8 quartiers, alors c’est le dernier tour de la partie.
- Les points sont calculés en fonction du prix des bâtiments construits.
- Le classement des joueurs est affiché par ordre décroissant.
 
 
### 6. Etat de la base de code : quelles parties sont bien faites ? Quelles parties sont à refactor et pourquoi ?

**Les parties bien faites** : 

- Gestion des cartes quartiers
- Utilisation de type énuméré et non pas de string
- Séparation des méthodes *communes* du joueur dans une classe dédiée
- Le squelette de la stratégie de jeu du joueur
- Une classe dédiée a l’entrée sortie
- Tests unitaires avec un coverage presque à 100%
- Nos classes sont toutes encapsulées
- Bonne gestion des résponsabilitées de chaque classe

**Les parties à refactor**:

- Les parties stratégies sont à refactor car elles ne prennent pas encore en compte le reste du jeu. Par exemple, les joueurs ne prennent pas en compte les autres joueurs dans la stratégie
- Implémentation des personnages
- Faire des ramifications de stratégies, une partie pour le choix et l'utilisation des rôles, une partie pour la construction des quartiers, une partie pour la construction des merveilles
- Stratégies prédéfinies, complètement déterministe

### 7. Architecture du projet 

![alt text](./assets/uml.png "uml.png")

La classe Main est le point d'entrée du programme, il lance un moteur de jeu (Game Engine) qui démarre une partie. 
Cette partie se déroule tout d'abord par une demande à chaque joueur de choisir une carte personnage. 

#### GameEngine#LaunchGame()
##### GameEngine#askPlayersRoleAndSortThemByRole(List<CharacterCard> characterCardDeckOfTheRound)
Pour le premier tour, le 1er joueur choisit en premier, pour les suivants c'est le roi du round précédent qui choisit en premier s'il y en a eu un. S'il n'y en a pas eu, le 1er joueur est le premier qui choisit son rôle.

Les joueurs sont triés dans l'ordre de leurs roles (Assassin en 1er, Voleur en 2e ...) puis ils jouent les uns après les autres.

#### GameEngine#LaunchGame() boucle for 
Chaque joueur s'il peut jouer choisit entre une pièce ou piocher une carte.
Ensuite on demande au joueur s'il veut d'abord construire puis recupérer les taxes ou l'inverse.
Le joueur construit et reccupère ses taxes. 
Puis le joueueur utilise son action de personnage (tuer, voler etc...)
Enfin le tour du joueur se finit par l'utilisation de ses (eventuelles) cartes merveilles

On vérifie si la partie est terminée : le joueur a 8 cartes construites. 

#### GameEngine#getWinner()
Le moteur de jeu vérifie pour chaque joueur s'il y a des points bonus à lui ajouter : 
- Il a fini en 1er, ou fini au même tour que le premier
- Cartes Merveilles qui ajoutent des points
 
Les joueurs sont triés en fonction des points qu'ils ont et affiché du meilleur au moins bon. 

Fin de l'execution du programme.

#### IO
Est le seul point de sortie autorisé pour le programme, toutes les autres classes doivent l'appeler pour pouvoir afficher quoi que ce soit dans la console. 
GameEngine, UniqueDistrictsEngine et PowerEngine sont les seules classes ayant le droit d'utiliser la classe IO, tout le reste du code envoie des objets et n'est donc pas verbeux et ne dois pas l'être. 

#### Player
Les joueurs sont appelés par le moteur de jeu pour faire des choix : 
Chaque joueur est initialisé avec une stratégie qui permet de choisir la bonne a chaque appel. 

Une classe PlayerTools permet d'avoir des statistiques sur le joueur comme la couleur la plus commune dans une liste de ses cartes par exemple. 

#### UniqueDistrictsEngine et PowerEngine
Sont des classes abstraites complémentaires au moteur de jeu qui permettent d'utiliser les pouvoirs de chaque joueur avec l'appel d'une seule méthode chacune. Ceci permet de limiter la taille de la classe moteur de jeu (Game Engine).

#### DeckOfCards
Est une classe qui contient et instancie toutes les cartes nécessaires pour le jeu. 
Les cartes ont uniquement des champs de type énuméré pour éviter les effets de bord. 


## Auteurs

- [@FlorianLatapie](https://github.com/FlorianLatapie)
- [@ThomasPaul](https://github.com/tom3883)
- [@LoicLeBris](https://github.com/LoicLeBris)
- [@MariusLesaulnier](https://github.com/MariusLesaulnier)
