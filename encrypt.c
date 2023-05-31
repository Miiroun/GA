#include <stdio.h>

enum EncryptionStandard {
  DES,
  AES,
  DHKE
};
/*
des
aes
Diffie-Helman key exchange
*/

int dataSize = 256;

char * desEncrypt(char dataIn) 
{
  char encData[256] = "Hi";
  return &encData[0];
}


int main()
{
  enum EncryptionStandard cryptionType;
  cryptionType = DES;



  FILE *inFile;
  inFile = fopen("data/dataIn.txt", "r");
  char dataIn[256] = "Hello";

  fgets(dataIn, dataSize, inFile);
  printf("%s", dataIn);
  fclose(inFile);

  char encData[256] = "a";
  if (cryptionType == DES)
  {
    char tempData[256] = "Hi";//desEncrypt(dataIn);
    for (size_t i = 0; i < dataSize; i++)
    {
      encData[i] = tempData[i];
    }
    
  }

  FILE *encFile;
  encFile = fopen("data/encryptedData.txt", "w");

  fprintf(encFile, encData);

  fclose(encFile);

  return 0;
}
