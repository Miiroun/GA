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

//int dataSize = 100;

char desEncrypt(char dataIn) 
{
  //char encData[100]; encData = dataIn;
  return "hi"//encData;
}


int main()
{
  enum EncryptionStandard cryptionType = DES;



  FILE *inFile;
  inFile = fopen("data/dataIn.txt", "r");
  char dataIn[100];

  fgets(dataIn, dataSize, inFile);
  printf("%s", dataIn);
  fclose(inFile);

  char encData[100];
  encData[0] = "a";
  if (cryptionType == DES)
  {
    char tempData[100] = desEncrypt(dataIn);
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
