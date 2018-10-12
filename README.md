# Official ProximaX P2P Storage Java SDK
![banner](https://proximax.io/wp-content/uploads/2018/03/ProximaX-logotype.png)

ProximaX is a project that utilizes the NEM blockchain technology with the IPFS P2P storage technology to form a very powerful proofing solution for documents or files which are stored in an immutable and irreversible manner, similar to the blockchain technology solutions.

# Getting started with ProximaX Storage Java SDK

The Storage SDK allows developers to store content on the blockchain. There are currently two primary functions available: Upload and Download.

## Generate Catapult test account

ProximaX has a running Catapult MIJIN_TEST blockchain network for testing and development purposes. The available node to connect to is http://52.221.231.207:3000.

Create a test account by using the [NEM2 CLI](https://nemtech.github.io/cli/overview.html#installation). 
[NodeJS](https://nodejs.org/en/download/) installation is required.

Install NEM2-CLI (use `sudo` as needed)

`npm install --global nem2-cli` 

Initiate account generation.
`nem2-cli account generate`

Enter details as required. Network type should be MIJIN_TEST and Node URL is http://52.221.231.207:3000
```
Introduce network type (MIJIN_TEST, MIJIN, MAIN_NET, TEST_NET): MIJIN_TEST
Do you want to save it? [y/n]: y
Introduce NEM 2 Node URL. (Example: http://localhost:3000): http://52.221.231.207:3000
Insert profile name (blank means default and it could overwrite the previous profile): my_test_acct
New Account:    SAETZX-GUDKPY-56DE5E-DJUJWP-357J3N-UODQP2-NPII
Public Key:     C67E508956FF8E5897AD2AE045F0C7B53ED5A12A9EF19A5943456EB488946A6E
Private Key:    0C44069C3A1D1D34AF80F8FC1D7258DAB8114C023C42B058A64268E48E5C4351
```

Save the generate keys and address.

## Get your XPX Test Tokens

Upload function of the storage SDK will consume XPX tokens.

Get XPX tokens at the [XPX faucet](https://proximaxcatapultfaucet.azurewebsites.net/)

## Adding Jitpack Repository
Storage SDK libraries are hosted on Jitpack. Add the JitPack repository on the build file.

**Maven**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

**Gradle**
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## Adding the storage SDK
Add the storage SDK as dependency. Replace the version with the latest available.

**Maven**
```xml
<dependency>
    <groupId>io.proximax</groupId>
    <artifactId>xpx2-java-sdk</artifactId>
    <version>0.1.0-beta.1</version>
</dependency>
```
**Gradle**
```xml
compile 'io.proximax:xpx2-java-sdk:0.1.0-beta.1'
```

**Groovy**
```xml
@Grapes(
    @Grab(group='io.proximax', module='xpx2-java-sdk', version='0.1.0-beta.1')
)
```

## Create connection config to use

The storage SDK needs to connect to the a file storage (eg. IPFS) and a blockchain network node (eg. Catapult) to do most of its functions.
The required connection config would depend on the peer setup applicable to the DApp.

There are primarily two peer setups for DApps
1. Local peer setup - the DApp would like to have its own IPFS node running locally linked to the ProximaX IPFS network. This setup keeps file copies on the local IPFS node which is ideal if performance is important.
2. Remote peer setup - the DApp is a thin client (eg. mobile app, web app) and would connect remotely to use the storage. This setup connects to the PromximaX storage node that encapsulates both file storage and blockchain node.
  
#### Connection config for local peer setup

A local peer setup requires individual connections to both blockchain network node and file storage node.  

Note: To ease the testing for development purposes, ProximaX have added IPFS nodes running with port 5001 open.The URLs are http://ipfs1.kyc.proximax.io:5001 and http://ipfs2.kyc.proximax.io:5001

```java
ConnectionConfig connectionConfig = ConnectionConfig.createWithLocalIpfsConnection(
    new BlockchainNetworkConnection(
            BlockchainNetworkType.MIJIN_TEST,
            "52.221.231.207",
            3000,
            HttpProtocol.HTTP),
    new IpfsConnection(
            "127.0.0.1",
            5001));
```

**Blockchain Connection parameters**

| field | required | allowed values | description |
| ----- | -------- | -------------- | ----------- |
| networkType | yes | should be one of Catapult's network eg. MIJIN_TEST | Catapult network which is required to do on some blockchain-related actions |
| apiHost | yes | domain or IP | the domain or IP of blockchain API |
| apiPort | yes | int | the port of blockchain API |
| apiProtocol | yes| http or https | the scheme used of blockchain API |

For running your own Catapult blockchain locally, please refer to [Catapult Service Bootstrap](https://github.com/tech-bureau/catapult-service-bootstrap).    

**IPFS Connection parameters**

| field | required | allowed values | description |
| ----- | -------- | -------------- | ----------- |
| apiHost |	yes | domain or IP | the domain or IP of local IPFS API |
| apiPort |	yes | int | the port of local IPFS API |

For running IPFS locally, please refer to [IPFS](https://ipfs.io/).


#### Connection config for remote peer setup

A remote peer setup only requires connection to ProximaX storage node. A blockchain connection can also be set to use a different node than the one associated with the storage node.

Note: ProximaX currently has no running storage node for development and testing purposes.

Here is an example of connecting to storage connection.   

```java
ConnectionConfig connectionConfig = ConnectionConfig.createWithStorageConnection(
    new StorageConnection(
            "127.0.0.1",
            8081,
            HttpProtocol.HTTP,
            "11111",
            "SDB5DP6VGVNPSQJYEC2X3QIWKAFJ3DCMNQCIF6OA"
            ));
```

###### Storage Connection parameters

| field | required | allowed values | description |
| ----- | -------- | -------------- | ----------- |
| apiHost | yes | domain or IP | the domain or IP of storage API |
| apiPort | yes | int | the port of storage API |
| apiProtocol | yes | http or https | the scheme used of storage API |
| bearerToken | yes | string | the bearer token to allow access to storage API |
| nemAddress | yes | string | the NEM address to allow access to storage API |

## Uploading content

The storage SDK allows upload of a variety of data like file and URL resource.

At the minimum, each upload requires the data being uploaded and the private key of the signer of blockchain transaction.

Here is the complete list of parameters that can be configured on each upload.

| field | required | allowed values | description |
| ----- | -------- | -------------- | ----------- |
| data | yes | any of the upload parameter data | the content to upload and its details |
| signerPrivateKey | yes | string of valid hex on the blockchain network | private key of signer of the blockchain transaction |
| recipientPublicKey | no | string of valid hex on the blockchain network | public key of recipient of the blockchain transaction <br><br> if both recipientPublicKey and recipientAddress are not provided, recipient will be also the signer |
| recipientAddress | no | string of valid hex on the blockchain network | address of recipient of the blockchain transaction <br><br> if both recipientPublicKey and recipientAddress are not provided, recipient will be also the signer |
| computeDigest | no <br><br> default is false | true or false | when true, computes the digest of data <br> when false, digest calculation is not done |
| detectContentType | no <br><br> default is false | true or false | determines whether to detect content type when not provided |
| transactionDeadline | no <br><br> default is 12 hours | 1 to 23 <br><br> in hours | determines how long the transaction can wait to be confirmed |
| privacyStrategy | no <br><br> default is no or plain privacy | any of the privacy strategy implementation <br><br> (see privacy strategy section) | the privacy strategy that will encrypt the data |

Here are the common details of an upload parameter data.

| field | required | allowed values | description |
| ----- | -------- | -------------- | ----------- |
| description | no | string <br><br> (200 char limit) | a searchable description for the data |
| metadata | no | string to string key-value map <br><br>(500 char limit based on JSON equivalent) | a searchable key-value map  where lookup by key and key-value can be done |
| name | no | string <br><br> (200 char limit) | a searchable name for the data |
| contentType | no | string of content type <br><br> (50 char limit) | the content type of the file uploaded |

The following are example on how to create the parameters for the different data that can be uploaded.

###### Building parameter for file upload
```java
File file = new File("test.txt");
UploadParameter param = UploadParameter
    .createForFileUpload(file, "<private key>")
    .build();
```

###### Building parameter for file upload with extra details
```java
File file = new File("test.txt");
UploadParameter param = UploadParameter
    .createForFileUpload(
            FileParameterData.create(
                    file, 
                    "file description", 
                    "file name",
                    "text/plain", 
                    singletonMap("filekey", "filename")),
            "<private key>")
    .build();
```

###### Building parameter for byte array upload
```java
byte[] bytearray = ...;
UploadParameter param = UploadParameter
    .createForByteArrayUpload(bytearray, "<private key>")
    .build();
```

###### Building parameter for byte array upload with extra details
```java
byte[] bytearray = ...;
UploadParameter param = UploadParameter
    .createForByteArrayUpload(
            ByteArrayParameterData.create(
                    bytearray, 
                    "byte array description", 
                    "byte array",
                    "application/pdf", 
                    singletonMap("bytearraykey", "bytearrayval")),
            "<private key>")
    .build();
```

###### Building parameter for string upload
```java
String string = "ProximaX";
UploadParameter param = UploadParameter
    .createForStringUpload(string, "<private key>")
    .build();
```

###### Building parameter for string upload with extra details
```java
String string = "ProximaX";
UploadParameter param = UploadParameter
    .createForStringUpload(
            StringParameterData.create(
                    string, 
                    "UTF-8", 
                    "string description", 
                    "string name",
                    "text/plain", 
                    singletonMap("keystring", "valstring")),
            "<private key>")
    .build();
```

###### Building parameter for URL resource upload
```java
URL url = new URL(...);
UploadParameter param = UploadParameter
    .createForUrlResourceUpload(url, "<private key>")
    .build();
```

###### Building parameter for URL resource upload with extra details
```java
URL url = new URL(...);
UploadParameter param = UploadParameter
    .createForUrlResourceUpload(
            UrlResourceParameterData.create(
                    url,
                    "url description",
                    "url name", 
                    "image/png", 
                    singletonMap("urlkey", "urlval")),
            "<private key>")
    .build();
```

###### Building parameter for zip upload from list of files
```java
List<File> filesToZip = ...;
UploadParameter param = UploadParameter
    .createForFilesAsZipUpload(filesToZip, "<private key>")
    .build();
```

###### Building parameter for zip upload from list of files with extra details
```java
List<File> filesToZip = ...;
UploadParameter param = UploadParameter
    .createForFilesAsZipUpload(
            FilesAsZipParameterData.create(
                    filesToZip, 
                    "zip description",
                    "zip name", 
                    singletonMap("zipkey", "zipvalue")),
            "<private key>")
    .build();
```

###### Building parameter for path upload

**Important note: Uploading path is by default public on IPFS. Please be careful when loading a directory as it exposes it the open public gateways**

This is only supported for local peer setup.

```java
File path = new File("../test");
UploadParameter param = UploadParameter
    .createForPathUpload(path, "<private key>")
    .build();
```

###### Building parameter for path upload with extra details

```java
File path = new File("../test");
UploadParameter param = UploadParameter
    .createForPathUpload(
            PathParameterData.create(
                    path, 
                    "path description", 
                    "path name", 
                    singletonMap("pathkey", "pathval")),
            "<private key>")
    .build();
```

#### Uploading using the parameter

Once the `UploadParameter` is ready, create an instance `Uploader` by passing the `ConnectionConfig` and then uploading using the parameter.
The `UploadResult` contains the blockchain transaction hash and the IPFS data hash which can be used to retrieve the uploaded content.
 

```java
Uploader uploader = new Uploader(connectionConfig);
UploadResult result = uploader.upload(param);

result.getTransactionHash(); // the blockchain transaction hash
result.getData().getDataHash(); // the IPFS data hash
```

## Downloading content
The Storage SDK supports two types of download.
- Complete download - the usual download which retrieves content together with its metainfo
- Direct download - the download which retrieves only the content 

### Complete download

A complete download is done by providing a hash of a blockchain transaction that has an uploaded content.

Here are the parameters.

| field | required | allowed values | description |
| ----- | -------- | -------------- | ----------- |
| transactionHash | yes | string of valid transaction hash | the blockchain transaction hash of an upload instance |
| privacyStrategy | no <br><br> default is no or plain privacy | any of the privacy strategy implementation <br><br> (see privacy strategy section) | the privacy strategy to decrypt the data
| validateDigest | no <br><br> default is false | true or false	| whether to validate the content is accurate. <br><br>ignored if digest is not calculated when content was uploaded |

Build the `DownloadParameter` which will be used to download.
```java
String transactionHash = ...;
DownloadParameter param = DownloadParameter.create(transactionHash).build();
```

Create a `Downloader` instance using the `ConnectionConfig` and download using the parameter.
The `DownloadResult` contains the content details and the content itself available as a stream.

```java
Downloader downloader = new Downloader(connectionConfig);
DownloadResult result = downloader.download(param);

result.getData().getByteStream(); // the stream of the content itself
result.getData().getContentType(); 
result.getData().getDescription(); 
result.getData().getName(); 
result.getData().getMetadata(); 
result.getData().getTimestamp(); // the timestamp of the upload
```

### Direct download
A direct download is done either by providing the blockchain transaction hash or the IPFS data hash.

Here are the parameters.

| field | required | allowed values | description |
| ----- | -------- | -------------- | ----------- |
| transactionHash | one of transactionHash and dataHash is required | string of valid transaction hash | the blockchain transaction hash of an upload instance |
| dataHash | one of transactionHash and dataHash is required | string of data hash of IPFS | hash for the uploaded data on IPFS |
| validateDigest | no <br><br> default is false <br><br> can be set only when download by transactionHash | true or false |  whether to validate the content is accurate. <br><br>ignored if digest is not calculated when content was uploaded |
| digest | no <br><br> can be set only when download by dataHash | sha-256 hex digest of data | digest to verify that the content is accurate |
| privacyStrategy | no <br><br> default is no or plain privacy | any of the privacy strategy implementation <br><br> (see privacy strategy section) | the privacy strategy to decrypt the data


Build the `DirectDownloadParameter` which will be used to download.

**if downloading by transaction hash**
```java
String transactionHash = ...;
DirectDownloadParameter param = 
    DirectDownloadParameter.createFromTransactionHash(transactionHash).build();
```

**if downloading by data hash**
```java
String dataHash = ...;
DirectDownloadParameter param = 
    DirectDownloadParameter.createFromDataHash(dataHash).build();
```

Create a `Downloader` instance using the `ConnectionConfig` and download using the parameter.
```java
Downloader downloader = new Downloader(connectionConfig);
InputStream result = unitUnderTest.directDownload(param);
``` 

## Securing content with Privacy Strategies
By default, any upload uses a plain privacy strategy and does not encrypt content.

In order to secure the content, privacy strategies can be configured as part of the UploadParameter creation. The same can be done on download to ensure data is properly decrypted.

Note: Setting privacy strategy has no effect when uploading path.

The following are list of available privacy strategies that out-of-the-box with the Storage SDK.

***NEM keys privacy strategy***

This uses a NEM private key and another public key to encrypt the content using Ed25519 ([EdDSA](https://en.wikipedia.org/wiki/EdDSA)).

```java
UploadParameter param = UploadParameter
    .createForStringUpload("test string", "<private key>")
    .withNemKeysPrivacy("<private key>", "<public key>")
    .build();
```

***Password privacy strategy***

This uses a password with at least 10 characters to encrypt the content.

```java
UploadParameter param = UploadParameter
    .createForStringUpload("test string", "<private key>")
    .withPasswordPrivacy("averysecuredpassword")
    .build();
```

***Custom privacy strategy***

Developers can implement their own encryption and decryption strategy by extending the `CustomPrivacyStrategy`. 

```java
UploadParameter param = UploadParameter
    .createForStringUpload("test string", "<private key>")
    .withPrivacyStrategy(new CustomPrivacyStrategy(){
        @Override
        public InputStream encryptStream(InputStream byteStream) {
            return null; // developer encryption strategy
        }

        @Override
        public InputStream decryptStream(InputStream byteStream) {
            return null; // developer decryption strategy
        }
    })
    .build();
```

## Asynchronous functions
All functions of Storage SDK can be called asynchronously. 

The SDK provides an `AsyncCallbacks` which would handle the result of functionality once ready. When an asynchronous function is called, it will send back an `AsyncTask` which holds the state of function call. Currently however, only the `done` flag is usable. 

**Sample for uploadAsync**
```java
AsyncTask asyncTask = uploader.uploadAsync(param, 
    AsyncCallbacks.create(
        (UploadResult result) -> System.out.println(result),
        (Throwable ex) -> System.out.println(ex)));

asyncTask.isDone(); // check done status
```

## Websocket / Monitoring / Listener
Catapult transactions can be monitored through [websockets](https://nemtech.github.io/api.html#tag/Websockets) 

NEM SDKs have Listener classes to simplify subscribing to websocket channels. It uses RxJava Observables.

Below is a quick example on how to use Listener to wait for upload transaction to be confirmed. 

```java
final Listener listener = new Listener("http://13.229.219.71:3000");
listener.open().get();

// wait for transaction to be confirmed
final Transaction transaction = listener.confirmed(Address.createFromRawAddress("<address of signer>"))
    .filter(confirmedTxn ->
            confirmedTxn.getTransactionInfo()
                    .flatMap(TransactionInfo::getHash)
                    .map(hash -> hash.equals("<upload transaction hash>"))
                    .orElse(false))
    .blockingFirst(); 
```

** Please note websocket monitoring is a pub-sub which means if a message was sent prior to listening, the message was missed already and not gonna arrive again.**

Here are some other usages of Listener. For more details, visit this [link](https://nemtech.github.io/guides/transaction/debugging-transactions.html).

```java
// wait for one any confirmed transaction of address
listener.confirmed(Address.createFromRawAddress("<an address>")).blockingFirst(); 

// wait for one any failed transaction of address
listener.status(Address.createFromRawAddress("<an address>")).blockingFirst(); 

// wait for one added unconfirmed transaction of address
listener.unconfirmedAdded(Address.createFromRawAddress("<an address>")).blockingFirst(); 

// wait for one removed unconfirmed transaction of address 
listener.unconfirmedRemoved(Address.createFromRawAddress("<an address>")).blockingFirst(); 

// wait for one added aggregated bonded transaction of address 
listener.aggregateBondedAdded(Address.createFromRawAddress("<an address>")).blockingFirst(); 

// wait for one removed aggregated bonded transaction of address 
listener.aggregateBondedRemoved(Address.createFromRawAddress("<an address>")).blockingFirst(); 

// wait for one added cosignature transaction of address 
listener.cosignatureAdded(Address.createFromRawAddress("<an address>")).blockingFirst(); 

// wait for one newly added block
listener.newBlock().blockingFirst(); 
```

Head over to [RxJava](https://github.com/ReactiveX/RxJava/wiki) to know more about Observables and what operators can be used.


## Error and Troubleshooting

Upload failures will result in `UploadFailureException`.
- Due announce transaction failures such as insufficient funds
- Due to announce transaction timeouts
- Due to connection failures to IPFS
- Due to connection failures to Blockchain node

Complete and direct download failures will generally return `DownloadFailureException` and `DirectDownloadFailureException`.
- Due to invalid transaction hash
- Due to transaction not yet confirmed on blockchain
- Due to not a transfer transaction with an upload
- Due to failed digest validation 
- Due to connection failures to IPFS
- Due to connection failures to Blockchain node

If the wrong privacy strategy is used, direct download and retrieval of data stream via `DownloadResult.getByteStream()` will result to IOException.


## Contribution
We'd love to get more people involve in the project. We're looking for enthusiastic conitrbutors that can help us improve the library. Contributing is simple, you can start by
+ Test the SDK and raise an issue.
+ Pick up a task, code and raise a PR

Copyright (c) 2018 ProximaX Limited


