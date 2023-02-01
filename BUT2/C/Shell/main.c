#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <fcntl.h>

typedef enum {
    MOT, TUB, INF, SUP, SPP, NL, FIN
} LEX;

static LEX getlex(char *mot) {
    enum {
        Neutre, Spp, Equote, Emot
    } etat = Neutre;
    int c;
    char *w;
    w = mot;
    while ((c = getchar()) != EOF) {
        switch (etat) {
            case Neutre:
                switch (c) {
                    case '<':
                        return (INF);
                    case '>':
                        etat = Spp;
                        continue;
                    case '|':
                        return (TUB);
                    case '"':
                        etat = Equote;
                        continue;
                    case ' ':
                    case '\t':
                        continue;
                    case '\n':
                        return (NL);
                    default:
                        etat = Emot;
                        *w++ = c;
                        continue;
                }
            case Spp:
                if (c == '>')
                    return (SPP);
                ungetc(c, stdin);
                return (SUP);
            case Equote:
                switch (c) {
                    case '\\':
                        *w++ = c;
                        continue;
                    case '"':
                        *w = '\0';
                        return (MOT);
                    default:
                        *w++ = c;
                        continue;
                }
            case Emot:
                switch (c) {
                    case '|':
                    case '<':
                    case '>':
                    case ' ':
                    case '\t':
                    case '\n':
                        ungetc(c, stdin);
                        *w = '\0';
                        return (MOT);
                    default:
                        *w++ = c;
                        continue;
                }
        }
    }
    return (FIN);
}
int changeDir(char *path) {
    if (chdir(path) == -1) {
        perror("The directory does not exist");
        return 1;
    }
    return 0;
}
char * getCurrentDir() {
    char *path = malloc(1024);
    getcwd(path, 1024);
    return path;
}
int main(int argc, char *argv[]) {
    char mot[200];
    char *arg[20];
    int argn = 0;
    FILE *input;
    FILE *output;
    short changeInput = 0;
    short changeOutput = 0;
    short appendOutput = 0;
    char *hostname = malloc(1024);
    gethostname(hostname, 1024);
    while (1) {
        if (argn == 0) {
            input = stdin;
            output = stdout;
            printf("%s@%s:%s$ ", getenv("USER"), hostname, getCurrentDir());
        }
        switch (getlex(mot)) {
            case MOT:
                if (changeInput) {
                    input = fopen(mot, "r");
                    changeInput = 0;
                    break;
                }
                if (changeOutput) {
                    output = fopen(mot, "w");
                    changeOutput = 0;
                    break;
                }
                if (appendOutput) {
                    output = fopen(mot, "a");
                    appendOutput = 0;
                    break;
                }
                if (strcmp(mot, "cd") == 0) {
                    getlex(mot);
                    changeDir(mot);
                    break;
                }
                arg[argn++] = strdup(mot);
                printf("\n");
                break;
            case TUB:
                printf("IDK what to do with TUB\n");
                break;
            case INF:
                changeInput = 1;
                break;
            case SUP:
                changeOutput = 1;
                break;
            case SPP:
                appendOutput = 1;
                break;
            case NL:
                arg[argn] = NULL;
                if (fork() == 0) {
                    // redirection
                    if (input != stdin) {
                        dup2(fileno(input), 0);
                        fclose(input);
                    }
                    if (output != stdout) {
                        dup2(fileno(output), 1);
                        fclose(output);
                    }
                    execvp(arg[0], arg);
                    perror("execvp");
                    exit(1);
                }
                wait(NULL);
                argn = 0;
                break;
            case FIN:
                printf("FIN \n");
                exit(0);
        }
    }
}
