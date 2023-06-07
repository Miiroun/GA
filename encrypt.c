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

char * desEncrypt(char *pData) 
{
  char *encData[256]; //dont nead to create a new array here
  encData[0] = pData;
  int i;
  for (i = 0; i < dataSize; i++)
  {
    //*encData[i] = *encData[i] + 1; // segmentation fault, hur fixar??
    
    
  }
  pData = encData[0]; //den läser just nu från random memorys

  return pData;
}


int main()
{
  enum EncryptionStandard cryptionType = DES;



  FILE *inFile;
  inFile = fopen("data/dataIn.txt", "r");
  char dataIn[256] = "Hello";

  fgets(dataIn, dataSize, inFile);
  printf("dataIn:%s, %d\n", dataIn, dataIn);

  fclose(inFile);

  char encData[256];
  if (cryptionType == DES)
  {
    char * pData;
    pData = &dataIn[0];
    char *tempData[256];
    tempData[0] = desEncrypt(pData);

    int i;
    // just nu så bara tar värdet för index noll av tempdata, behöver flytta hela stringen, fixat tror jag
    printf("CED: ["); //change encrypyed data:
    for (i = 0; i < dataSize; i++)//datasize
    {
      //*tempData[i] = *tempData[i] + 100; // flyttar stringen en bit framåt, byt ut till matrix för cesar chifer,
      //borde vara andra del av kod
      printf(" %c: %u,",*tempData[i], *tempData[i]); //change encrypyed data:
      encData[i] = *tempData[i];
      //printf("%c",encData[i]);//1

      if (i != dataSize)
      {
        tempData[i+1] = tempData[i] + 1; //vad gör denna kod? pointer magi?, min kod är äklig
      }      
    }
    printf("]\n");

    //sju första karaktärerna är korupta
    printf("%c",encData[8]);//2 något fel sker med encData mellan utmarkerat 1&2, vet inte riktigt vad
    
  }
  //vad är problemet med encData här?
  printf("encryptedData:%s, %d\n",encData, encData);

  FILE *encFile;
  encFile = fopen("data/encryptedData.txt", "w");
  fprintf(encFile, encData);
  fclose(encFile);

  return 0;
}
