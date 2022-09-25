#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <unistd.h>
int main(int argc, char const *argv[])
{
    // check if the number of arguments is correct
    if (argc < 2 )
    {
        printf("Usage: <Command> \n");
        return 1;
    }
    for (int i = 1; i < argc; i++)
    {
        switch (fork())
        {
        case -1:
            printf("Error: fork() failed");
            return 1;
        default:
            break;
        case 0:
            execlp(argv[i],argv[i],NULL);
            exit(0);
        }
    }
    return 0;
}
