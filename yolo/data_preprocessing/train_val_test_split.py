import os
import glob
import shutil
from sklearn.model_selection import train_test_split

# getting list of image
image_files = glob.glob("C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/labels/*")
images = [name.replace(".txt","") for name in image_files]

# splitting the dataset
# training : val : test = 8 : 1 : 1
train_names, test_names = train_test_split(images, test_size=1/11, random_state=42, shuffle=True)
val_names, test_names = train_test_split(test_names, test_size=0.5, random_state=42, shuffle=True)

def batch_move_files(file_list, source_path, destimation_path):
    for file in file_list:
        # 경로에서 마지막 파일명만 가져와서 확장자 붙여줌
        image = file.split('\\')[-1] + '.jpg'
        txt = file.split('\\')[-1] + '.txt'

        shutil.move(os.path.join(source_path,  'images/', image), os.path.join(destimation_path + "images/"))
        shutil.move(os.path.join(source_path, 'labels/', txt), os.path.join(destimation_path + "labels/"))
    return

# data path
source_dir = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/"

# new data path
train_dir = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/train/"
val_dir = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/val/"
test_dir = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/test/"

batch_move_files(train_names, source_dir, train_dir)
batch_move_files(test_names, source_dir, test_dir)
batch_move_files(val_names, source_dir, val_dir)