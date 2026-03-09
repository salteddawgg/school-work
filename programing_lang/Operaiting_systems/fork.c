#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"
#include "kernel/fcntl.h"

int
main(int argc, char *argv[])
{
    int pid = fork();

    if(pid < 0){
    
        fprintf(2, "fork failed\n");
        exit(1);
    }

    if(pid == 0){
        // Child process

        // Close input (fd = 0)
        close(0);

        // Open input.txt (will take lowest available fd = 0)
        int fd = open("input.txt", O_RDONLY);
        if(fd < 0){
            fprintf(2, "cannot open input.txt\n");
            exit(1);
        }

        //  cat
        char *args[] = { "cat", 0 };
        exec("cat", args);

        
        fprintf(2, "exec failed\n");
        exit(1);
    } 
    else {
        // Parent process waits for child
        wait(0);
    }

    exit(0);
}
