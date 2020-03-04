#ifndef __TCP_CLIENT_H__
#define __TCP_CLIENT_H__

#include <netdb.h> 
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <sys/socket.h> 
#include <arpa/inet.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>

#define MAX 80 
#define PORT 8080 
#define SA struct sockaddr 

class TCPClient {

    private:
    char buff[MAX];
    int n;
    void (*communication) (int);
    int sockfd, connfd;
    struct sockaddr_in servaddr, cli;
    int port;
    int len;

    void func (int sockfd) {
        char buff[MAX];
        for (;;) { 
            bzero(buff, sizeof(buff)); 
            printf("Enter the string : ");
            while ((buff[n++] = getchar()) != '\n') 
                ; 
            write(sockfd, buff, sizeof(buff)); 
            bzero(buff, sizeof(buff)); 
            read(sockfd, buff, sizeof(buff)); 
            printf("From Server : %s", buff); 
            if ((strncmp(buff, "exit", 4)) == 0) { 
                printf("Client Exit...\n"); 
                break; 
            }
        }
    }
    
    public:
    TCPClient() {
        n = 0;
    };
    TCPClient(int);


    void createSocket() {
        sockfd = socket(AF_INET, SOCK_STREAM, 0); 
    if (sockfd == -1) { 
        printf("socket creation failed...\n"); 
        exit(0); 
    } 
    else
        printf("Socket successfully created..\n"); 
    bzero(&servaddr, sizeof(servaddr)); 
    }

    void listn(){
        servaddr.sin_family = AF_INET; 
        servaddr.sin_addr.s_addr = inet_addr("127.0.0.1"); 
        servaddr.sin_port = htons(PORT); 
    
        // connect the client socket to server socket 
        if (connect(sockfd, (SA*)&servaddr, sizeof(servaddr)) != 0) { 
            printf("connection with the server failed...\n"); 
            exit(0); 
        } 
        else
            printf("connected to the server..\n");
        func(sockfd);
    }
};
#endif