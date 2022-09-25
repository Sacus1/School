#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
int main(int argc, char const *argv[])
{
    // check if the number of arguments is correct
    // arg0 is the name of the program
    // arg1 is the name of the directory
    // arg2 is the name of the file to be created in the directory
    if (argc < 3)
    {
        printf("Usage: <Directory> <File> \n");
        return 1;
    }
    switch (fork())
    {
    case -1:
        printf("Error in fork");
        return 1;
    default:
        wait(NULL);
        // combine the directory and the file name
        char *path = malloc(strlen(argv[1]) + strlen(argv[2]) + 2);
        strcpy(path, argv[1]);
        strcat(path, "/");
        strcat(path, argv[2]);
        execlp("touch", "touch", path, NULL);
        break;
    case 0:
        execlp("mkdir", "mkdir", argv[1], NULL);
        exit(0);
        break;
    }
    return 0;
}
