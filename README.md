# 🐕   AI Lost Pet Search App  
<br/> 

### 1. &nbsp; Background of Development <br/><br/>  
- _Recently, the number of dog owners in Korea is increasing. According to statistics from the Animal Freedom Solidarity, more than 10 million people own dogs. Unfortunately, the number of abandoned animals is also increasing according to this trend. When dog owners lose their dogs, they are very worried about what to do. At this time, the first thing to do is to go to a place where the dog is likely to be and find it. To solve these problems more easily, I devised "AI Lost Pet Search App"._ <br/><br/><br/>

### 2. &nbsp; Project Introduction <br/><br/>  
- _This application is an SNS-type application that helps dog owners easily find their lost dogs._  <br/>
 
- _In addition, the application provides free information on abandoned dog shelters in Gyeonggi-do._ <br/><br/><br/>
 
### 3. &nbsp; Main Function <br/><br/> 
- _**Sign Up & Sign In**_ <br/><br/>
<img src="https://github.com/qortmdgh4141/AI_Lost_Pet_Search_App/blob/main/image/main_registration_login.png?raw=true"  width="640" height="240"> <br/>
  - _You must sign up for membership to use the application._ <br/>
    - _If you enter your ID and password in email format, your membership will be completed._ <br/>
    - _If you have already signed up, you can skip the above procedure and log in._ <br/><br/>
    
  - _Users who have completed their membership can register information such as their profile picture, name, contact number, date of birth, address, etc._ <br/> <br/> <br/>

- _**Registering a Bulletin Board**_ <br/><br/>
<img src="https://github.com/qortmdgh4141/AI_Lost_Pet_Search_App/blob/main/image/registering_a_post.png?raw=true"  width="1280" height="300"> <br/>
  - _If a passerby finds an abandoned dog wandering alone on the street, the passerby can register information such as the place and time of discovery along with a picture of the dog._ <br/>
    - _It's okay not to have knowledge of dog breeds when writing a bulletin board._ <br/>
    - _This is because the application automatically informs the dog's breed information using the YOLO model._ <br/><br/>
    
  - _At this time, users who register the bulletin board will be given 500 points._ <br/>
    - _For users who also take protective measures, 50,000 points will be given._ <br/><br/>
    
  - _On the contrary, owners who lose their dogs have to pay 100 points to access the information in the bulletin board._ <br/>
    - _In the case of user bulletin boards that are even taking protective measures, they have to pay 5000 points._ <br/><br/><br/>
    
- _**Bulletin Board of Abandoned Dog Shelter Located in Gyeonggi-Do**_ <br/>
<img src="https://github.com/qortmdgh4141/AI_Lost_Pet_Search_App/blob/main/image/the_bulletin_board_of_the_abandoned_dog_shelter.png?raw=true"  width="960" height="380"> <br/><br/>
  -  _Users can check information about abandoned dog shelters in Gyeonggi-do for free._ <br/><br/><br/>

### 4. &nbsp; YOLO Model Training Strategies Using Transfer-Learning & Fine-Tuning <br/><br/>

- _**Transfer-Learning & Fine-Tuning Definition**_ <br/>

  - _Transfer learning consists of taking features learned on one problem, and leveraging them on a new, similar problem._ <br/>
  - _Transfer learning is usually done for tasks where your dataset has too little data to train a full-scale model from scratch._ <br/>
  - _The most common incarnation of transfer learning in the context of deep learning is the following workflow._ <br/>
    1. _Take layers from a previously trained model._ <br/>
    2. _Freeze them, so as to avoid destroying any of the information they contain during future training rounds._ <br/>
    3. _Add some new, trainable layers on top of the frozen layers. They will learn to turn the old features into predictions on a new dataset._ <br/>
    4. _Train the new layers on your dataset._ <br/>
  - _A last, optional step, is fine-tuning, which consists of unfreezing the entire model you obtained above (or part of it), and re-training it on the new data with a very low learning rate._ <br/><br/><br/> 
  
 - _**My Fine-Tuning Strategy**_ <br/> <br/>
 <img src="https://github.com/qortmdgh4141/AI_Lost_Pet_Search_App/blob/main/image/transfer_learning_fine_tuning_2.png?raw=true"  width="1320" height="490"> <br/>
   
|Strategy|Method |Feature|
|:-----------------------:|:--------------:|:-----------------------:|
|_**Strategy 1**_|_Train the entire model. In this situation, it is possible to use the architecture of the pre-trained model and train it according to the dataset._|_It is recommended for large datasets._|
|_**Strategy 2**_|_Train some layers and leave the others frozen. In a CNN architecture, lower layers refer to general features (problem independent), while higher layers refer to specific features (problem dependent). In this case, we have to adjust the weights of the network._|_This option is useful when we have a small dataset and a large number of parameters, we need to leave more layers frozen to avoid overfitting. On the other hand, if the dataset is large and the number of parameters is small, it is possible to improve the model by training more layers to the new task._|
|_**Strategy 3**_|_Freeze the convolutional base. In this situation, we have an extreme case of the train/freeze trade-off. The rationale behind it is to keep the original form of the convolutional base to use as input for the classifier. By this way, the pre-trained model plays the role of a feature extractor._|_It can be interesting for small datasets or if the problem solved by the pre-trained model is similar to the one we are working on._| 

<br/><br/> 


 - _**Results Based on 3 Strategies**_ <br/> <br/>
  <img src="https://github.com/qortmdgh4141/AI_Lost_Pet_Search_App/blob/main/image/transfer_learning_fine_tuning.png?raw=true"  width="1280" height="340"> <br/> <br/> 
    - _**Strategy 1** &nbsp; : &nbsp; Yellow, &nbsp;&nbsp;&nbsp;&nbsp; **Strategy 2 &nbsp; :** &nbsp; Pink, &nbsp;&nbsp;&nbsp;&nbsp; **Strategy 3 &nbsp; :** &nbsp; Purple_  <br/> 
    - _Strategy 1 shows the best results._ <br/>
    - _I think the reasons for this result are as follows._ <br/>
    - _The dataset I used is a large dataset and has little resemblance to the dataset of pre-trained models_ <br/> <br/> <br/>

--------------------------
### 💻 S/W Development Environment
<p>
  <img src="https://img.shields.io/badge/Windows 10-0078D6?style=flat-square&logo=Windows&logoColor=white"/>
  <img src="https://img.shields.io/badge/Android Studio-3DDC84?style=flat-square&logo=Android&logoColor=white"/> 
  <img src="https://img.shields.io/badge/Firebase-blue?style=flat-square&logo=Firebase&logoColor=FFCA28"/>
</p>  
<p>
  <img src="https://img.shields.io/badge/PyCharm-66FF00?style=flat-square&logo=PyCharm&logoColor=black"/>
  <img src="https://img.shields.io/badge/NVIDIA-black?style=flat-square&logo=NVIDIA&logoColor=76B900"/>
  <img src="https://img.shields.io/badge/Anaconda-e9e9e9?style=flat-square&logo=Anaconda&logoColor=44A833"/>
</p>
<p>
  <img src="https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=Python&logoColor=white"/>
  <img src="https://img.shields.io/badge/PyTorch-FF9900?style=flat-square&logo=PyTorch&logoColor=EE4C2C"/>
  <img src="https://img.shields.io/badge/NumPy-013243?style=flat-square&logo=Numpy&logoColor=blue"/>
  <img src="https://img.shields.io/badge/Java-FF0000?style=flat-square&logo=Java&logoColor=white"/> 
</p>   

### 🚀 Deep Learning Model
<p>
  <img src="https://img.shields.io/badge/YOLO-0000FF?"/>
</p>

### 💾 Datasets used in the project
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; COCO Dataset <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; AI Hub Dataset &nbsp; : &nbsp; Animal Videos to Distinguish Pets. <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Gyeonggi Data Dream Dataset &nbsp; : &nbsp; Status of Abandoned Animal Protection. <br/>
