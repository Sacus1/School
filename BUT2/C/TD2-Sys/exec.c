#include <unistd.h>
#include <stdio.h>
int main(int argc, char const *argv[])
{
    // check if the number of arguments is correct
    if (argc < 2 )
    {
        printf("Usage: <Command> \n");
        return 1;
    }
    printf("%d",execlp(argv[1],argv[1],NULL));
    return 0;
}
