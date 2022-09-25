/* */
/* Utilisation de PIPE inter processus */
/* */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
/*
descripteur de pipe
* pip2[0] :  lecture
* pip2[1] : ecriture
*/
int pip2[2];
/*
 descripteur de pipe
* pip1[0] :  lecture
* pip1[1] : ecriture
 */
int pip1[2];

/* Le fils lit dans le pipe */
void p1()
{
    char *q[3];
    q[0] = "Est-ce que ca compile ?";
    q[1] = "Est-ce que ca marche ?";
    q[2] = "Est-ce que ca tourne ?";
    int i = 0;
    close(pip1[0]);
    close(pip2[1]);
    for (i = 0; i < 3; i++)
    {
        // ask question
        write(pip1[1], q[i], strlen(q[i]) + 1);
        // read answer
        char a[100];
        read(pip2[0], a, 100);
        printf("\nAnswer : %s ", a);
    }
    return;
}

/* Le père ecrit dans le pipe */
void p2()
{
    char *reponse[3];
    reponse[0] = "Oui";
    reponse[1] = "Non";
    reponse[2] = "Peut-être";
    // read question
    char q[100];
    int i = 0;
    close(pip1[1]);
    close(pip2[0]);
    for (i = 0; i < 3; i++)
    {
        // read question
        read(pip1[0], q, 100);
        // answer question
        write(pip2[1], reponse[i], strlen(reponse[i]) + 1);
        printf("\nQuestion : %s\n", q);
    }
    return;
}
int main(int argc, char **argv)
{
    if (pipe(pip2) || pipe(pip1)) /* Ouverture d'un pipe */
    {
        perror("Erreur de pipe");
        exit(1);
    }
    printf("Dernier message avant fork\n");
    switch (fork()) /* Creation d'un processus */
    {
    case -1:
        perror("Erreur de fork");
        exit(1);
    case 0:
        p1();
        break;
    default:
        p2();
        break;
    }
    printf("\nFin programme");
}