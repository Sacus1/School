#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
/* Commande de compilation : gcc  -o  SAE1.02  SAE1.02.c  
  Commande d'execution : ./SAE1.02 fichier.csv
*/
// Definition liste chainée
struct Cell
{
    char *prenom;
    int Nombre;
    int DateMin; // Date de première apparition , par defaut sur 3000 car tout le monde est né avant l'an 3000
    int DateMax; // Date de dernière apparition , par defaut sur 0 car tout le monde est né après l'an 0
    char Genre; //1 pour Homme , 2 Pour femme
    struct Cell *Suivant;
};
typedef struct Cell Cell;
typedef  Cell *Liste;
Liste liste_vide = (Liste)NULL; //On defini la liste_vide pour simplifié la lecture

void EffacerListe(Liste L){
    // Pour liberer la memoire et ainsi empecher un usage rémanant de celle ci après utilisation
    if (L == liste_vide) return ;
    Liste Next = L->Suivant; // On stock la cellule suivante dans une variable pour ne pas la perdre après effacement de celle selectionné
    while ( Next != liste_vide){  
        Next = L->Suivant;
        free(L);
        L = Next;
    }
    if (L != liste_vide )
        free(L);
}

Liste CreeListe(Liste L,char *prenom){
    Liste ret = malloc(sizeof(Cell)); //On alloue de la memoire pour ne pas perdre la variable en sortie de fonction
    ret->prenom = strdup(prenom);
    ret->DateMax = 3000;
    ret->DateMin = 0;
    ret->Nombre = 0;
    ret->Suivant = L;
    return ret;
}

void Majuscule(char *s)
{
    while(*s) {*s=toupper(*s);s++;} //On parcours tout les caractère de la chaine et les met en majuscule
}

void RecherchePrenom(char *chooseName,Liste prenoms,int Genre){
    int NHom = 0; //Le nombre de personne avec ce prenom et de sexe masculin
    int D1st =  3000; //Date de première apparition
    int Dend = 0; // Date de dernière apparition
    int NFem = 0;//Le nombre de personne avec ce prenom et de sexe feminin
    Liste prenoms2 = prenoms;
    while (prenoms2->Suivant != liste_vide){
        if (strcmp(prenoms2->prenom,chooseName) == 0){  //On verifie si le prenom dans la cellule actuel est le meme que celui voulu
            if  (D1st > prenoms2->DateMin) //Si la date de première apparition dans la cellule actuel est plus ancienne que celle deja inscrit dans D1st alors on actualise D1st
                D1st = prenoms2->DateMin;
            if (Dend < prenoms2->DateMax)//Si la date de dernière apparition dans la cellule actuel est plus recente que celle deja inscrit dans Dend alors on actualise Dend
                Dend = prenoms2->DateMax;
            if (prenoms2->Genre == 1){ //Si c'est un prenom masculin on prend son nombre d'individu pour le mettre dans NHom
                NHom = prenoms2->Nombre;
            }
            else { //Si c'est un prenom feminin on prend son nombre d'individu pour le mettre dans NFem
                NFem = prenoms2->Nombre;
            }
    }
    prenoms2 = prenoms2->Suivant;
    }
    if (NFem+NHom == 0) // Si il n'y a aucun individu de ce nom
        printf("Le prenom %s n'est jamais apparu\n",chooseName);
    else{
    if (Genre) { //Si on sépare par genre
        printf("Le prénom %s a été donné à %d garçons et %d filles.",chooseName,NHom,NFem);
    }
    else{
        int N = NFem+NHom;
        printf("Le prénom %s a été donné à %d enfants.\n",chooseName,N);
    }
    if (D1st < 3000) //Si il y a une date de première apparition
        printf("Année de première apparition %d\n",D1st);
    if (Dend>0) //Si il y a une date de dernière apparition
        printf("Année de dernière apparition %d\n",Dend);
    }
}
void Selections(int PHom,int PFem,int PTotal,int total,Liste Lettres[27]){
    int choix;
    char ans[5]; //Chaine pour récuperer tout ce qui est contenu dans le buffer du terminal
    while (1){ //Boucle infini qui s'arrete avec un return , elle est ralenti par un scanf a chaque iteration
        printf("Que voulez-vous afficher ? (0 pour le menu) > ");
        scanf("%s",ans);
        if (sscanf(ans,"%d",&choix) == 1) //On met dans choix le première character entrer si c'est un nombre sinon on revient au debut de la boucle
        {
            switch (choix) 
            {
            case 1:
                printf("Il y a au total %d naissance depuis 1900\n",total);
                break;
            case 2:
            {
                int choix2;
                printf("Differencier par genre ?(1 : Oui sinon Non) :");
                scanf("%d",&choix2);
                switch (choix2)
                {
                case 1:
                    printf("Il y a %d prenoms feminins et %d prenoms masculins dans le fichier\n",PFem,PHom);
                    break;
                default:
                    printf("Il y a %d prenoms dans le fichier\n",PTotal);
                    break;
                } 
            }
                break; 
            case 3:
            {
                char chooseName[20] ;
                printf("Quel prenom ? ");
                scanf("%s",chooseName);
                int choix2;
                printf("Differencier par genre ?(0: Non sinon Oui) :");
                scanf("%d",&choix2);
                Majuscule(chooseName);
                Liste L = Lettres[chooseName[0] <'A'|| chooseName[0] > 'Z'?26:chooseName[0]-'A']; //On enleve 65 au code de la lettre pour revenir a un intervale 0;25 et si c'est un caractère special alors on va a l'indice 26;
                RecherchePrenom(chooseName,L,choix2); 
            }
                break;
            case 4:
                return ;
                break;
            default: 
                printf("\n— 0 : Ce menu\n— 1 : Le nombre de naissances\n— 2 : Le nombre de prénoms\n— 3 : Statistiques sur un prénom\n— 4 : Quitter\n");
                break;
            }
        }
    }
}
int* Generation(FILE *fichier,Liste * Lettres)
{
    char *line = NULL;
    size_t len = 0;
    ssize_t nread ;
    getline(&line,&len,fichier); //On recupère la première ligne au debut afin de l'ignoré car elle ne contient pas de donné utile
    int precDate = 0; //On stock la date precedante afin d'avoir la date de dernière apparition 
    char PrecIndice = 0; // On stock l'indice precedant afin de pouvoir rentrer la date de dernière apparition
    int PHom = 0 ;int PFem= 0;int PTotal= 0;int total = 0;
    while ((nread=getline(&line,&len,fichier)) != -1){ //On ecrit dans line la ligne lu, et revoie -1 a la fin du fichier  donc fin de la boucle
        char sexe =atoi(&strsep(&line,";")[0]); //On recupère la premier caractère de la chaine du sexe et le transforme en entier, on le stock dans un char pour ne pas prendre place inutile
        char *prenom =strsep(&line,";"); 
        int date = atoi(strsep(&line,";"));//On convertie la date en entier
        strsep(&line,";"); //On ignore la ligne du departement car non utilisé
        int nombre = atoi(strsep(&line,";")); //On convertie en entier le nombre d'individu
        char Indice = prenom[0] <'A'|| prenom[0] > 'Z'?26:prenom[0]-'A'; //On enleve 65 au code de la lettre pour revenir a un intervale 0;25 et si c'est un caractère special alors on va a l'indice 26; 
        total+= nombre;
        if (Lettres[Indice] == liste_vide || strcmp(Lettres[Indice]->prenom,prenom) || Lettres[Indice]->Genre != sexe){ //Si il n'y a pas de prenom pour cette indice ou que le prenom enregistrer est different de celui lu , ou que le sexe est different alors on crée une nouvelle liste avec le nouveau prenom
            if (PrecIndice)
                Lettres[PrecIndice]->DateMax = precDate;//On defini la dernière date dans l'indice precedant
            if (sexe == 1) { //Si c'est un prenom masculin
                PHom ++; 
                PTotal++; 
                Lettres[Indice] = CreeListe(Lettres[Indice],prenom);
            }
            else { //Si c'est un prenom feminin
                PFem ++;
                Liste L = Lettres[Indice] ;
                if (L != liste_vide){ //On verifie qu'il y a un prenom dans la liste avec cette indice
                    while (L->Suivant != liste_vide && !strcmp(prenom,L->prenom)) { //On parcours la liste jusqu'a ce qu'on trouve le meme prenom ou qu'on arrive au bout de la liste
                        L = L->Suivant;
                    }
                if (strcmp(prenom,L->prenom))  //Si le prenom n'est pas le meme 
                    PTotal++;
                }
                else  //Sinon le prenom est unique
                    PTotal ++;
                Lettres[Indice] = CreeListe(Lettres[Indice],prenom);
            }
            Lettres[Indice]->DateMin = date;
            Lettres[Indice]->DateMax = date;
            Lettres[Indice]->Genre = sexe;            
        }
        if (date != 0) //Si il y a une date
            precDate = date;
        PrecIndice = Indice;
        Lettres[Indice]->Nombre += nombre;
    }
    int *Data =(int*)malloc(sizeof(int)*4); //On alloue la memoire pour la liste pour pouvoir renvoyer plusieurs element 
    Data[0] =PHom;Data[1] = PFem;Data[2] = PTotal;Data[3] = total;
    return Data;
}
int main(int argc, char *argv[]){
    if (argc > 1){ //Si il y a un argument de rentrer
        FILE *fichier;
        fichier = fopen(argv[1],"r");  //On ouvre le fichier mis en argument
        if (!fichier){ 
            printf("Impossible d'ouvrir le fichier");
            return 1;
        }
        Liste Lettres[27] ;//On defini le tableau de 27 element de Liste chainée : Les 26 premier correspond au lettre normal et le 27 eme au caractère spéciaux
        memset(Lettres,0,27*sizeof(Liste)); //On defini les 27 element comme une Liste
        int *Data = Generation(fichier,Lettres);
        Selections(Data[0],Data[1],Data[2],Data[3],Lettres);
        //Fermeture du fichier et liberation des variable
        fclose(fichier); 
        free(Data);
        for (int i  = 0 ; i<26 ; i++) EffacerListe(Lettres[i]);
        return 0;
    }
    else  //Si aucun argument
        printf("Fichier non renseigner");
        return 1;
}
 
