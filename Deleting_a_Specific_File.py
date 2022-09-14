import os

# AI hub 라벨링 음수로 잘못 기입한 이미지 리스트
delete_list =["20201104_dog-lying-000569.mp4_frame_390_timestamp_13000",
"20201104_dog-lying-000569.mp4_frame_630_timestamp_21000",
"20201111_dog-tailing-000619.mp4_frame_12_timestamp_400",
"20201111_dog-tailing-000619.mp4_frame_18_timestamp_600",
"20201111_dog-tailing-000619.mp4_frame_24_timestamp_800",
"20201114_dog-bodylower-000355.mp4_frame_684_timestamp_22800",
"20201114_dog-bodylower-001715.mp4_frame_268_timestamp_11167",
"20201114_dog-bodylower-001715.mp4_frame_278_timestamp_11583",
"20201114_dog-bodylower-001715.mp4_frame_297_timestamp_12375",
"20201115_dog-tailing-001606.mp4_frame_557_timestamp_18585",
"20201118_dog-bodylower-000317.mp4_frame_149_timestamp_4972",
"20201118_dog-bodylower-000317.mp4_frame_167_timestamp_5572",
"20201118_dog-bodylower-000317.mp4_frame_173_timestamp_5772",
"20201118_dog-bodylower-000317.mp4_frame_185_timestamp_6173",
"20201118_dog-bodylower-000317.mp4_frame_215_timestamp_7174",
"20201118_dog-bodylower-000317.mp4_frame_233_timestamp_7774",
"20201120_dog-footup-002219.mp4_frame_276_timestamp_9200",
"20201120_dog-footup-002219.mp4_frame_306_timestamp_10200",
"20201120_dog-footup-002219.mp4_frame_312_timestamp_10400",
"20201120_dog-footup-002219.mp4_frame_318_timestamp_10600",
"20201202_dog-bodylower-007784.mp4_frame_522_timestamp_17400",
"20201202_dog-feetup-003360.mp4_frame_413_timestamp_13780",
"20201202_dog-feetup-003360.mp4_frame_419_timestamp_13981",
"20201202_dog-feetup-003360.mp4_frame_425_timestamp_14181",
"20201202_dog-footup-002242.mp4_frame_60_timestamp_2000",
"20201202_dog-walkrun-004026.mp4_frame_228_timestamp_7600",
"20201202_dog-walkrun-004026.mp4_frame_618_timestamp_20600"]
# AI hub 라벨링 음수로 잘못 기입한 이미지 리스트(파일명) 정보를 활용하여 삭제
def list_del_files(path):
  num = 0
  for root , dirs, files in os.walk(path):
    for name in files:
      img_name = name[:-4]
      if img_name in delete_list:
        num += 1
        os.remove(os.path.join(root, name))
        print ("Delete File: " + os.path.join(root, name))
  return num
#path = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/images/"
#number = list_del_files(path)

# json_to_txt.py로 올바르게 라벨링 파일을 뽑아내었는지 테스트
def lable_test_files(path,num):
  for root, dirs, files in os.walk(path):
    for name in files:
      if name.startswith("dog"):
        if int(name[0:8]) > 20201202:
          print("Delete File: " + os.path.join(root, name))
          num += 1
  return num
# num = 0
# label_path = 'C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/labels/'
# num = lable_test_files(label_path, num)
# print(num)

# json_to_txt.py로 정제된 txt 라벨링 파일의 파일명과 일치하지 않는 txt파일 & image파일 삭제
# 테스트 : 정제된 라벨링 txt 파일 개수 확인
'''Data_preprocessing_lable_names = os.listdir("C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/labels/")
total_image_names = os.listdir("C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/images/")
label_names = [name.replace(".txt",".jpg") for name in Data_preprocessing_lable_names]
num = 0
for i in label_names:
  if i in total_image_names:
    print(i)
    num +=1
print(num)'''
# AI hub json 파일 어노테이션 정보(파일명)와 실제이미지의 파일명이 불일치함에 따른 불일치한 txt 라벨 파일 삭제 함수
def lable_del_files(path, total_image_names):
  num = 0
  for root , dirs, files in os.walk(path):
    for label_name in files:
      label_jpg_name = label_name[:-4] + '.jpg'
      if label_jpg_name in total_image_names:
        num += 1
        print(num)
      else:
          os.remove(os.path.join(root, label_name))
#total_image_names = os.listdir("C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/images/")
#label_path = 'C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/labels/'
#lable_del_files(label_path, total_image_names)

# AI hub json 파일 어노테이션 정보(파일명)와 실제이미지의 파일명이 불일치함에 따른 불일치한 jpg 이미지 파일 삭제 함수
def image_del_files(path, total_label_names):
  num = 0
  for root , dirs, files in os.walk(path):
    for image_name in files:
      image_txt_name = image_name[:-4] + '.txt'
      if image_txt_name in total_label_names:
        num += 1
        print(num)
      else:
          os.remove(os.path.join(root, image_name))
# total_data_preprocessing_label_names = os.listdir("C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/labels/")
# image_path = 'C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/images/'
# image_del_files(image_path, total_data_preprocessing_label_names)



