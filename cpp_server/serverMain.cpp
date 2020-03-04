#include "TCPServer.h"

using namespace std;

int main(){
    TCPServer server = TCPServer();

    server.createSocket();
    server.listn();

}