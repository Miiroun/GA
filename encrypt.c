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

char * desEncrypt(char *data) 
{
  char encData[256];
  encData[0] = *data;
  *data = encData [0];
  return &data[0];
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
    char * pData;
    pData = &dataIn[0];
    char *tempData[256];
    tempData[0] = desEncrypt(pData);

    int i;
    for (i = 0; i < dataSize; i++)
    {
      encData[i] = *tempData[i];
    }
    
  }

  FILE *encFile;
  encFile = fopen("data/encryptedData.txt", "w");

  fprintf(encFile, encData);

  fclose(encFile);

  return 0;
}
