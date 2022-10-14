#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
/*
    stdin,stdout,stderr
    f,          stdout, g
    f,          h,         stderr
*/
int main(int argc, char *argv[])
{
    int f = open("f", O_CREAT | O_RDWR, 0666);
    int g = open("g", O_CREAT | O_RDWR, 0666);
    int h = open("h", O_CREAT | O_RDWR, 0666);
    int pid = fork();
    switch (pid)
    {
    case -1:
        printf("Error");
        break;
    case 0:
        // fils1
        close(0);
        dup(f);
        switch (fork())
        {
        case -1:
            printf("Error");
            break;
        case 0:
            // fils2
            close(1);
            dup(h);
            fprintf(stderr, "Error in fils2\n");
            printf("out fils2\n");
            break;
        default:
            // fils1
            close(2);
            dup(g);
            fprintf(stderr, "Error in fils1\n");
            printf("out fils1\n");
            break;
        }
    }
}