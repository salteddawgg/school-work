#include "kernel/types.h"
#include "kernel/stat.h"
#include "user/user.h"
#include "kernel/fcntl.h"

int
main(void)
{
    int fd;

    // Create directory
    if(mkdir("/dir") < 0){
        printf("Failed to create /dir\n");
    } else {
        printf("Directory /dir created\n");
    }

    
    fd = open("/dir/file", O_CREATE | O_WRONLY);
    if(fd < 0){
        printf("Failed to create /dir/file\n");
    } else {
        printf("File /dir/file created\n");
        close(fd);   // Properly close file descriptor
    }

    // Create device file
    if(mknod("/console", 1, 1) < 0){
        printf("Failed to create /console\n");
    } else {
        printf("Device file /console created\n");
    }

    exit(0);
}