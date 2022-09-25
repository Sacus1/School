
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
int main(int argc, char *argv[])
{

    int zombie = 0;
    if (argc < 2 || argc > 3)
    {

        printf("Usage: <number of children> <zombie? (default : 0)> \n");
        return 1;
    }
    if (argc ==  3){
        zombie = atoi(argv[2]);
    }
    int n = atoi(argv[1]);
     int status ;
    pid_t pid ;
    for (int i = 1; i < n+1; i++)
    {
        switch (fork())
        {
        case 0:
            printf("Je suis le fils %d, mon père est %d\n", getpid(), getppid());
            sleep(2*i);
            exit(i);
            break;

        default:
            pid = wait(&status);
            printf("Fils %d terminé avec le code %d\n", pid, status);
            printf("Je suis le père %d, mon PPID est %d\n", getpid(), getppid());
            break;

        case -1:
            printf("Error in fork");
            return 1;
        }
        if (zombie == 1){
            printf("Waiting\n");
            sleep(30*n);
        }
    }
    return 0;
}