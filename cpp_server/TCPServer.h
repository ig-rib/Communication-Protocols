#ifndef __TCP_SERVER_H__
#define __TCP_SERVER_H__

#include <iostream>
#include <netinet/in.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#define PORT 8080
#define MAX 10240
typedef struct sockaddr SA;

class TCPServer {

    private:
    void func(int sockfd) 
    { 
        char buff[MAX]; 
        int n; 
        // infinite loop for chat 
        for (;;) { 
            bzero(buff, MAX); 
    
            // read the message from client and copy it in buffer 
            read(sockfd, buff, sizeof(buff)); 
            // print buffer which contains the client contents 
            printf("From client: %s\t To client : ", buff); 
            bzero(buff, MAX); 
            n = 0; 
            // copy server message in the buffer 
            while ((buff[n++] = getchar()) != '\n') 
                ; 
    
            // and send that buffer to client 
            write(sockfd, buff, sizeof(buff)); 
    
            // if msg contains "Exit" then server exit and chat ended. 
            if (strncmp("exit", buff, 4) == 0) { 
                printf("Server Exit...\n"); 
                break; 
            } 
        } 
    } 
    int sockfd, connfd, len; 
    struct sockaddr_in servaddr, cli; 

    public:
    void createSocket();
    void listn();
};

#endif