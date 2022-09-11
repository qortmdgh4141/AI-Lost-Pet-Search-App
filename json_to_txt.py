import os
import json
from tqdm import tqdm
import shutils

# 현재 경로 확인
print(os.getcwd())

# bbox 좌표 Yolo 형식으로 변환
def convert_bbox_coco2yolo(img_width, img_height, bbox):
    """
    Convert bounding box from COCO  format to YOLO format
    Parameters
    ----------
    img_width : int
        width of image
    img_height : int
        height of image
    bbox : list[int]
        bounding box annotation in COCO format:
        [top left x position, top left y position, width, height]
    Returns
    -------
    list[float]
        bounding box annotation in YOLO format:
        [x_center_rel, y_center_rel, width_rel, height_rel]
    """

    # YOLO bounding box format: [x_center, y_center, width, height]
    # (float values relative to width and height of image)
    x_tl, y_tl, w, h = bbox

    dw = 1.0 / img_width
    dh = 1.0 / img_height

    x_center = x_tl + w / 2.0
    y_center = y_tl + h / 2.0

    x = x_center * dw
    y = y_center * dh
    w = w * dw
    h = h * dh

    return [x, y, w, h]

#  Label 폴더 없을 경우에만 생성
def make_folders(path="output"):
    if os.path.exists(path):
        shutils.rmtree(path)
    os.makedirs(path)
    return path

# Label 폴더안에 필요한 정보만 json -> txt 형식으로 변환
def convert_coco_json_to_yolo_txt(output_path, json_file):
    #for i in file_list_py:
    with open(json_file, encoding="UTF-8") as f:
        json_data = json.load(f)

    # write _darknet.labels, which holds names of all classes (one class per line)
    # _darknet.label 파일에 클래스의 정보 저장 ==> 추후 사용할지 말지 모르겠음

    '''label_file = os.path.join(output_path, "_darknet.labels")
    with open(label_file, "w", encoding="UTF-8") as f:
            category = json_data["metadata"]["animal"]
            category_name = category["breed"]
            img_height = json_data["metadata"]["height"]
            img_width  = json_data["metadata"]["width"]
            f.write(f"{category_name}\n")'''

    # AI hub json 파일 어노테이션 정보 불일치함에따라 폴더명 일치하게 변경
    img_dir_name = json_data["file_video"]
    if img_dir_name[0:4] != '2021':
        img_dir_name = img_dir_name.replace('/', '_')
    else :
        img_dir_name = img_dir_name[9:]

    category = json_data["metadata"]["animal"]
    category_name = category["breed"]
    img_height = json_data["metadata"]["height"]
    img_width = json_data["metadata"]["width"]

    # jpg 이미지 각각 맞게 annotation 정보를 뽑아와서 저장
    for image in tqdm(json_data["annotations"], desc="Annotation txt for each image"):
        if category_name == "푸들" :
            img_id = 0
        elif category_name == "말티즈" :
            img_id = 1
        elif category_name == "믹스견" :
            img_id = 2
        elif category_name == "포메라니안" :
            img_id = 3
        elif category_name == "비숑 프리제" :
            img_id = 4
        elif category_name == "치와와" :
            img_id = 5
        elif category_name == "재패니스 스피츠" :
            img_id = 6
        elif category_name == "시추" :
            img_id = 7
        elif category_name == "시바" :
            img_id = 8
        else :
            img_id = 9

        img_name = "_frame_" + str(image["frame_number"]) + "_timestamp_" + str(image["timestamp"])
        #img_width = image["width"]
        #img_height = image["height"]


        # AI hub 라벨링 음수로 잘못 기입한 경우 잘못된 이미지 이름 리스트 저장
        if float(image["bounding_box"]["x"]) < 0 or float(image["bounding_box"]["y"]) < 0 or float(image["bounding_box"]["width"]) < 0 or float(image["bounding_box"]["height"]) < 0:
            img_remove.append(img_dir_name + str(img_name))

        else:
            anno_txt = os.path.join(output_path, img_dir_name + str(img_name) + ".txt")
            with open(anno_txt, "w", encoding="UTF-8") as f:
            # for anno in anno_in_image:
                category = img_id
                bbox_COCO = [image["bounding_box"]["x"], image["bounding_box"]["y"], image["bounding_box"]["width"], image["bounding_box"]["height"]]
                x, y, w, h = convert_bbox_coco2yolo(img_width, img_height, bbox_COCO)

                # 이미지의 가로세로 높이가 같을 경우 0.9999... 초기화
                if float(f"{w:.10f}") >= 1:
                    w = 0.9999999999
                if float(f"{h:.15f}") >= 1:
                    h = 0.9999999999
                f.write(f"{category} {x:.10f} {y:.10f} {w:.10f} {h:.10f}\n")

#make_folders("yolov5/output")
## 해당 경로에 있는 .json 파일명 리스트 가져오기
path = 'C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/one_val_label'
file_list = os.listdir(path)
file_list_py = [file for file in file_list if file.endswith('.json')]

print(len(file_list_py))

# AI hub 라벨링 음수로 잘못 기입한 이미지 리스트
img_remove = []

for i in file_list_py :
    one_json_name = path + "/" + i
    output = 'C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/labels'
    print(one_json_name)
    convert_coco_json_to_yolo_txt(output, one_json_name)

print("--------------------------------------------")
for i in img_remove:
    print(i)
# convert_coco_json_to_yolo_txt("", "DOG_label/FEETUP/20201111_dog-feetup-000886.mp4.json")
