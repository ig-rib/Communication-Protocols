#include "TCPClient.h"

int main () {
    TCPClient client = TCPClient();
    client.createSocket();
    client.listn();
}