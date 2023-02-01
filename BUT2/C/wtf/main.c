/* client	*/

#include <stdio.h>

#include <sys/types.h>

#include <sys/socket.h>

#include <netinet/in.h>

#include <netdb.h>

#include <stdlib.h>
#include <string.h>


#define BUFFER_SIZE 200

#define NORMAL 0

int main(int argc, char **argv) {

    int s;

    int len;

    struct sockaddr_in sa;

    struct hostent *hp;

    struct servent *sp;

    /*  descripteur de socket

      stucture adresse Internet

      structure service de nom

      structure service Internet */

    char *myname;

    char buf[BUFFER_SIZE];

    char *host, *user;

    myname = argv[0];

    if (argc != 3) {

        fprintf(stderr, "Usage : %s serveur user\n", myname);

        exit(1);

    }

    user = argv[2];

    host = argv[1];

    if ((hp = gethostbyname(host)) == NULL) {

        fprintf(stderr, "%s : %s serveur inconnu\n", myname, host);

        exit(1);

    }

    /*pointeur sur le nom du programme

    pointeurs sur le serveur et l'utilisateur

    test des arguments

    voir si le serveur existe,

    structure hp remplie  */


    bcopy((char *) hp->h_addr, (char *) &sa.sin_addr, hp->h_length);

    sa.sin_family = hp->h_addrtype;

    if ((sp = getservbyname("msp", "tcp")) == NULL) {

        fprintf(stderr, "%s : Pas de service QUI sur ce syst�me \n", myname);

        exit(1);

    }

    sa.sin_port = sp->s_port;

    if ((s = socket(hp->h_addrtype, SOCK_STREAM, 0)) < 0) {

        perror("socket");

        exit(1);

    }
    fprintf(stdout, "Service de numero de port %d et de nom %s\n", sp->s_port, sp->s_name);

    fprintf(stdout, "Service %hu demand� � %s\n", sa.sin_port, host);

    fprintf(stdout, "Type d'adresse %d ; descripteur de socket %d\n", sa.sin_family, s);

    /*copie de l'adresse du serveur et du type d'adresse dans sa

    voir si le service est autoris� sur le client,

    rempli la structure sp

    num�ro du service

    cr�ation de la socket client, allocation par le syst�me */


    if (connect(s, &sa, sizeof(sa)) < 0) {

        perror("connect");

        exit(1);

    }

    send(s, user, strlen(user) + 1, NORMAL);

    recv(s, buf, BUFFER_SIZE, NORMAL);

    fprintf(stdout, "R�ponse : %s \n", buf);

    close(s);

    exit(0);
}
