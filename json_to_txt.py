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

# Label 폴더 없을 경우에만 생성
def make_folders(path="output"):
    if os.path.exists(path):
        shutil.rmtree(path)
    os.makedirs(path)
    return path

# Label 폴더안에 필요한 정보만 json -> txt 형식으로 변환
def convert_coco_json_to_yolo_txt(output_path, json_file):

    with open(json_file, encoding="UTF-8") as f:
        json_data = json.load(f)

    # write _darknet.labels, which holds names of all classes (one class per line)
    # _darknet.label 파일에 클래스의 정보 저장 ==> 추후 사용할지 말지 모르겠음
    label_file = os.path.join(output_path, "_darknet.labels")
    with open(label_file, "w", encoding="UTF-8") as f:
            category = json_data["metadata"]["animal"]
            category_name = category["breed"]
            img_height = json_data["metadata"]["height"]
            img_width  = json_data["metadata"]["width"]

            f.write(f"{category_name}\n")

    # jpg 이미지 각각 맞게 annotation 정보를 뽑아와서 저장
    for image in tqdm(json_data["annotations"], desc="Annotation txt for each image"):
        img_id = 0
        img_name = "frame_" + str(image["frame_number"]) + "_timestamp_" + str(image["timestamp"])
        #img_width = image["width"]
        #img_height = image["height"]

        anno_txt = os.path.join(output_path, str(img_name) + ".txt")

        with open(anno_txt, "w", encoding="UTF-8") as f:
            # for anno in anno_in_image:
                category = img_id
                bbox_COCO = [image["bounding_box"]["x"], image["bounding_box"]["y"], image["bounding_box"]["width"], image["bounding_box"]["height"]]
                x, y, w, h = convert_bbox_coco2yolo(img_width, img_height, bbox_COCO)
                f.write(f"{category} {x:.6f} {y:.6f} {w:.6f} {h:.6f}\n")


convert_coco_json_to_yolo_txt("output", "DOG_label/FEETUP/20201111_dog-feetup-000886.mp4.json")

