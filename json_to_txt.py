import json
from tqdm import tqdm
import shutils

# 현재 경로 확인
print(os.getcwd())


#dog_class_dic = {"푸들": 0, "말티즈": 0, "믹스견": 0, "포메라니안": 0, "비숑 프리제": 0,  "치와와": 0,  "재패니스 스피츠": 0, "시추":0,  "시바" :0}
dog_class_dic = { '푸들': 0, '말티즈': 0, '포메라니안': 0, '비숑 프리제': 0, '치와와': 0, '재패니스 스피츠': 0, '시추': 0, '시바': 0, '닥스훈트': 0, '파피용': 0, '웰시 코기 (펨브록)': 0,
                 '래브라도 리트리버': 0, '베들링턴 테리어': 0, '진돗개': 0, '요크셔 테리어': 0, '아메리칸 코커 스패니얼': 0, '보더 콜리': 0, '시베리안 허스키': 0,  '골든 리트리버': 0, '카발리에 킹 찰스 스패니얼': 0 }
dog_class_dic_id = { '푸들': 0, '말티즈': 1, '포메라니안': 2, '비숑 프리제': 3, '치와와': 4, '재패니스 스피츠': 5, '시추': 6, '시바': 7, '닥스훈트': 8, '파피용': 9, '웰시 코기 (펨브록)': 10,
                 '래브라도 리트리버': 11, '베들링턴 테리어': 12, '진돗개': 13, '요크셔 테리어': 14, '아메리칸 코커 스패니얼': 15, '보더 콜리': 16, '시베리안 허스키': 17,  '골든 리트리버': 18, '카발리에 킹 찰스 스패니얼': 19 }

# 품종 클래스별 개수 확인
def check_data_class(output_path, json_file):
    #for i in file_list_py:
    with open(json_file, encoding="UTF-8") as f:
        json_data = json.load(f)

    category = json_data["metadata"]["animal"]
    category_name = category["breed"]

    # jpg 이미지 각각 맞게 annotation 정보를 뽑아와서 저장
    for image in tqdm(json_data["annotations"], desc="Annotation txt for each image"):
        if category_name in dog_class_dic:
            dog_class_dic[category_name] += 1
        else :
            dog_class_dic[category_name] = 1

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

# Label 폴더안에 필요한 정보만 json -> txt 형식으로 변환
def convert_coco_json_to_yolo_txt(output_path, json_file):
    #for i in file_list_py:
    with open(json_file, encoding="UTF-8") as f:
        json_data = json.load(f)



    # AI hub json 파일 어노테이션 정보 불일치함에따라 폴더명 일치하게 변경
    img_dir_name = json_data["file_video"]
    #if img_dir_name[0:4] != '2021':
    #    img_dir_name = img_dir_name.replace('/', '_')
    if img_dir_name[0:4] != '2021':
        img_dir_name = img_dir_name.replace('/', '_')
        # AI hub json 파일 어노테이션 정보(파일명)와 실제이미지의 파일명이 불일치함에 따른 불일치한 txt 라벨 파일은 저장하지 않음
        if img_dir_name[0:4] == '2020' and img_dir_name[0:8] > '20201202':
            return 0
    else :
        img_dir_name = img_dir_name[9:]
        img_dir_name = img_dir_name.replace('.mp4', '')

    category = json_data["metadata"]["animal"]
    category_name = category["breed"]
    img_height = json_data["metadata"]["height"]
    img_width = json_data["metadata"]["width"]

    # jpg 이미지 각각 맞게 annotation 정보를 뽑아와서 저장
    for image in tqdm(json_data["annotations"], desc="Annotation txt for each image"):
        # 20개의 클래스를 제외한 품종은 저장하지 않음
        if category_name in dog_class_dic:
            # 2200개 이상 저장한 클래스는 저장하지 않음
            if dog_class_dic[category_name] < 2400 :
                img_id = dog_class_dic_id[category_name]
                dog_class_dic[category_name] += 1
            else :
                return 0
        else :
            return 0

        img_name = "_frame_" + str(image["frame_number"]) + "_timestamp_" + str(image["timestamp"])

        # AI hub 라벨링 음수로 잘못 기입한 경우 잘못된 이미지 이름 리스트 저장
        if float(image["bounding_box"]["x"]) < 0 or float(image["bounding_box"]["y"]) < 0 or float(image["bounding_box"]["width"]) < 0 or float(image["bounding_box"]["height"]) < 0:
            img_remove.append(img_dir_name + str(img_name))
            dog_class_dic[category_name] -= 1
            break
        else:
            anno_txt = os.path.join(output_path, img_dir_name + str(img_name) + ".txt")
            with open(anno_txt, "w", encoding="UTF-8") as f:
                category = img_id
                bbox_COCO = [image["bounding_box"]["x"], image["bounding_box"]["y"], image["bounding_box"]["width"], image["bounding_box"]["height"]]
                x, y, w, h = convert_bbox_coco2yolo(img_width, img_height, bbox_COCO)

                # 이미지의 가로세로 높이가 같을 경우 0.9999... 초기화
                if float(f"{w:.10f}") >= 1:
                    w = 0.9999999999
                if float(f"{h:.15f}") >= 1:
                    h = 0.9999999999
                f.write(f"{category} {x:.10f} {y:.10f} {w:.10f} {h:.10f}\n")

# 해당 경로에 있는 .json 파일명 리스트 가져오기
path = 'C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/one_labels'
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
    #check_data_class(output, one_json_name)

print("--------------------------------------------")
for i in img_remove:
    print(i)
print(img_remove)
print(f'AI hub 라벨링 음수로 잘못 기입한 이미지 리스트 개수 :  {len(img_remove)}')

print("--------------------------------------------")
print(f'저장한 클래스 각각 개수  :  {len(dog_class_dic)}')
print(dog_class_dic)
