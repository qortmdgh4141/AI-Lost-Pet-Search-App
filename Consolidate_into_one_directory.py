import os
import shutil
import time

# 폴더 안에 있는 모든 하위 파일(서브폴더의 파일 포함)을 읽어 리스트로 반환하는 함수를 정의
# 반복문과 재귀 함수를 이용해서 하위 폴더의 파일까지 모두 접근
def read_all_file(path):
    output = os.listdir(path)
    file_list = []
    for i in output:
        if os.path.isdir(path + "/" + i):
            file_list.extend(read_all_file(path + "/" + i))
        elif os.path.isfile(path + "/" + i):
            file_list.append(path + "/" + i)

    return file_list

# 폴더 내의 모든 하위 파일들을 새로운 경로로 복사하는 함수를 정의
def copy_all_file(file_list, new_path):
    for src_path in file_list:

        # 만약 Label 이라면
        file_name = src_path.split("/")[-1]
        shutil.copyfile(src_path, new_path + '/' + file_name)

        # 만약 image 라면
        #file_name = src_path.split("/")[-1]
        #dir_name = src_path.split("/")[-2]
        #shutil.copyfile(src_path, new_path + '/' +  dir_name +"_" + file_name)

        print("파일 {} 작업 완료".format(file_name))  # 작업한 파일명 출력

start_time = time.time() # 작업 시작 시간

# 정의한 함수들을 실행하여 폴더 안의 모든 하위 파일들(서브 폴더의 파일 포함)을 복사해서 또다른 하나의 폴더로 합침
# src_path에는 기존 폴더의 경로를 적어주고, new_path에는 파일들을 옮길 새로운 폴더 경로를 적음
src_path = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/labels"  # 기존 폴더 부모 폴더 경로
new_path = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/one_labels"  # 옮길 폴더 경로

file_list = read_all_file(src_path)
copy_all_file(file_list, new_path)

print("=" * 40)
print("러닝 타임 : {}".format(time.time() - start_time))  # 총 소요시간 계산
