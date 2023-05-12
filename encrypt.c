#include <stdio.h>

/*
0 = des
1 = aes
2 = Diffie-Helman key exchange
*/
int cryptionType = 0;
int dataSize = 100;

char desEncrypt(char dataIn[dataSize]) {
  char encData[dataSize] = dataIn;
  return encData;
}


int main()
{
  FILE *inFile;
  inFile = fopen("data/dataIn.txt", "r");
  char dataIn[dataSize];

  fgets(dataIn, dataSize, inFile);
  printf("%s", dataIn);
  fclose(inFile);

  char encData[dataSize];
  if (cryptionType == 0)
  {
    encData = desEncrypt(dataIn);
  }

  FILE *encFile;
  encFile = fdopen("data/encryptedData.txt", "w");

  fprintf(encFile, encData);

  fclose(encFile);

  return 0;
}
