#include "types.h"
#include "stat.h"
#include "user.h"
#include "fcntl.h"

int
main(void)
{
  int p[2];
  int pid;

  // Create pipe
  if(pipe(p) < 0){
    printf(1, "pipe failed\n");
    exit();
  }

  pid = fork();

  if(pid < 0){
    printf(1, "fork failed\n");
    exit();
  }

  // CHILD 
  if(pid == 0){
    close(0);            // Close input (FD 0)
    dup(p[0]);           // Make stdin refer to read end of pipe

    close(p[0]);         // Close original pipe
    close(p[1]);

    char *argv[] = { "wc", 0 };
    exec("wc", argv);    

    printf(1, "exec failed\n");
    exit();
  }

  // PARENT 
  else {
    close(p[0]);         // Close read 

    write(p[1], "hello world\n", 12);

    close(p[1]);         // Close write 

    wait();              // Wait for child to finish
  }

  exit();
}