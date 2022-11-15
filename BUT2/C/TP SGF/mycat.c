#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>


/* Exercice 1
VERSION ouverture et lecture de fichier
-Que fait ce programme mycat.c ; tester
-Donner un équivalent myfcat.c qui utilise fread fwrite

int main (int argc, char *argv[])
{
    int i;
    int byte_lu;
    int fd;
    char tampon [1024];
    for (i=1; i<argc; i++)
    {
        fd= open(argv[i],O_RDONLY);
        if(fd==-1)
        {
            printf("%s file not exist check the name\n", argv[i]);
            return(2);
        }

        else{
            while((byte_lu=read(fd,tampon,1024)) > 0)
                write(STDOUT_FILENO,tampon,byte_lu);
            close(fd);
        }
    }
    return(0) ;
}
  Le code ci-dessus lit le contenu d'un fichier et l'affiche sur la sortie standard.
  Equivalent avec fwrite et fread:
int main (int argc, char *argv[])
{
    int i;
    int byte_lu;
    int fd;
    char tampon [1024];
    for (i=1; i<argc; i++)
    {
        fd= open(argv[i],O_RDONLY);
        if(fd==-1)
        {
            printf("%s file not exist check the name\n", argv[i]);
            return(2);
        }

        else{
            while((byte_lu=fread(tampon,1,1024,fd)) > 0)
                fwrite(tampon,1,byte_lu,stdout);
            close(fd);
        }
    }
    return(0) ;
}

Exercice 2
En utilisant read et write,
Faire un mycat1.c qui est un équivalent de la commande cat et qui est lancé ainsi: ./mycat1 </usr/lib/libvlccore.so.8.0.0 >tss

*/

int main (int argc, char *argv[])
{
    int byte_lu;
    char tampon [1024];
    while((byte_lu=read(STDIN_FILENO,tampon,1024)) > 0)
        write(STDOUT_FILENO,tampon,byte_lu);
    return(0) ;
}
/*
**** Version 1 :

avec char  c[1024] et lecture par bloc de 1024

time ./mycat1 </usr/lib/libvlccore.so.8.0.0 >tss

real	0m0.004s
user	0m0.000s
sys	0m0.000s

**** Version 2 :

avec char c et lecture par caractère

time ./mycat1 </usr/lib/libvlccore.so.8.0.0 >tss

real	0m1.488s
user	0m0.084s
sys	0m1.364s

Comparer et expliquer les résultats des 2 versions.

 Lire le fichier par bloc de 1024 octets est plus rapide que par caractère car on lit 1024 octets à la fois et on écrit 1024 octets à la fois.

*/

/*
même 2 versions de myfcat1 avec fread fwrite

Comparer et expliquer les résultats des 2 versions de mycatf1.

Comparer avec les résultats de mycat1 et conclure

*/