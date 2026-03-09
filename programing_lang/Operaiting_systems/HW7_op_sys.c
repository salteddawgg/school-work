#include <stdio.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <unistd.h>

int main() {
    int fd;
    struct stat fileStat;

    // Create or open file named "a"
    fd = open("a", O_CREAT | O_RDWR, 0644);
    if (fd < 0) {
        perror("Error opening file");
        return 1;
    }

    // Use fstat() to retrieve file information
    if (fstat(fd, &fileStat) < 0) {
        perror("Error getting file status");
        close(fd);
        return 1;
    }

    // Print the inode number
    printf("Inode number: %lu\n", fileStat.st_ino);

    // Close the file
    close(fd);

    // Exit the program
    return 0;
}