# Rétrospective du projet Citadelles 

Starfish de notre projet
https://app.funretrospectives.com/agendas/-MqyF4N36KL35iCYlS_7

Pour la fin de ce projet Citadelles, nous revenons sur notre organisation et le déroulement du projet du début à la fin. Pour cela nous avons fixé cinq points à discuter : 
 - Qu’avons-nous fait de trop ? 
 - Qu’avons-nous fait insuffisamment ? 
 - Qu’avons-nous bien fait ? 
 - Qu’aurions-nous dû faire ? 
 - Que faut-il arrêter de faire ? 

Nous allons détailler point par point dans la suite de cette synthèse. 

 
### Qu’avons-nous fait de trop ?  

Pendant tout le déroulement du projet, nous avions un train de retard sur la planification des milestones. Alors que nous étions censés projeter nos actions sur le long terme dès le début en créant les cinq milestones, nous avons préféré définir au fur et à mesure la suite des opérations. Chaque séance de projet était dédiée à déterminer les prochaines issues plutôt que de se concentrer sur le code. Nous aurions dû tout définir dès le début, et redéfinir petit à petit en fonction de notre avancement dans le projet et ainsi prendre moins de temps à planifier les milestones. 

### Qu’avons-nous fait insuffisamment ? 

De façon plus concrète, certaines choses dans le code n’ont pas été suffisamment prises en compte pour le développement du jeu. Le premier exemple qui nous vient est la création de stratégies pour les robots et pour les joueurs. Selon nous, l’idée d’implémenter une interface pour les stratégies est une bonne idée. Cela nous permet de créer plusieurs stratégies différentes et de pouvoir choisir comme on le désire la stratégie à appliquer pour le déroulement de la partie. Cependant, cette idée n’a pas été suffisamment exploitée. A l’heure actuelle, seules deux stratégies sont implémentées, dont une qui fait tous ses choix de façon aléatoire. Si nous devions revenir en arrière, nous aurions sûrement concentré une partie du travail sur l’ajout de plusieurs autres stratégies. 

### Qu’avons-nous bien fait ? 

Pour rendre le code lisible et accessible à tous les membres du groupe, nous avons défini dès le début que nous créerions des méthodes avec des noms clairs et explicites, quitte à avoir des noms longs. Selon l’ensemble des membres du groupe, nous avons eu une bonne idée en faisant ça. Plus d’une fois nous avons pu récupérer le code d’un coéquipier sans forcément avoir à demander des explications sur son code. Les rares fois où cela a pu arriver, la communication entre les membres était suffisamment complète pour qu’aucun d’entre nous ne se retrouve perdu. Par moment, le code ne nous satisfaisait plus, que ce soit parce qu’on ne le trouvait pas propre, ou parce qu’il n’était pas pratique. Dans ces moments-là, nous prenions un temps pour reprendre le code entier et mener une remise en forme de celui-ci. Nous pensons que c’est une bonne chose et que cela nous a permis de rendre un code propre et bien organisé. 

### Qu’aurions-nous dû faire ? 

Les stratégies qui ont été implémentées ne sont pas suffisantes mais elles sont aussi utilisables seulement une par une.
Nous aurions aimé avoir la possibilité de donner des stratégies différentes à chaque joueur afin de simuler au mieux une vraie partie de jeu.
Dans notre code actuel, seule une stratégie est utilisable pour l’ensemble des joueurs. Donner une stratégie différente pour chaque joueur aurait permis de confronter les stratégies. En prenant un grand échantillon de démonstrations du jeu, nous aurions pu déterminer les stratégies les plus intéressantes pour gagner une partie. Dans la même idée, il aurait fallu avoir des stratégies générales, qui comprennent à la fois les stratégies liées aux quartiers et aux personnages. Également, certaines règles du jeu n’ont pas été bien respectées. Par exemple, dans le cas où sept joueurs sont présents, la possibilité de choisir entre deux personnages pour le dernier joueur. Cette fonctionnalité n’est pas vraiment compliquée à implémenter mais n’a pas été faite pour autant. 

### Que faut-il arrêter de faire ? 

L’organisation du groupe est globalement bonne. Le seul point à supprimer pour mieux s’organiser selon nous, est d’arrêter d’utiliser GitHub sur la seule branche master. Trop souvent, deux membres du groupe ont codé sur la branche principale de GitHub sans forcément en informer les autres. Cela créer des problèmes de code qui se confrontent. Pour bien faire, il aurait fallu que chacun code sur sa propre branche pour tout mettre en commun après, en évitant ces erreurs simples. Également, pour rendre le code plus intéressant, nous avons choisi de nommer toutes nos variables et méthodes en anglais. Nous étions tous d’accord pour dire que c’était une bonne idée mais cela amène à des fautes d’anglais et à des oublis (écriture en français). Certains bouts de code ont été mis sur GitHub alors qu’ils ne respectaient pas cette règle du code en anglais. Nous sommes donc d’accord pour dire qu’il faudrait arrêter de soumettre notre code sur GitHub avant d’avoir vérifié que tout était bien en anglais. 

En conclusion, le projet s’est bien déroulé du début à la fin. Certaines choses n’étaient pas optimales dans l’organisation du groupe mais cela nous a permis de nous en rendre compte et nous pourrons nous appuyer dessus pour les projets à venir. 
