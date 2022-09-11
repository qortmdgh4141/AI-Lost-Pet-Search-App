import os

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

import os

def del_files(path):
  num = 0
  for root , dirs, files in os.walk(path):
    for name in files:
      img_name = name[:-4]
      if img_name in delete_list:
        num += 1
        os.remove(os.path.join(root, name))
        print ("Delete File: " + os.path.join(root, name))
  return num
path = "C:/Users/user/PycharmProjects/Intelligent_CCTV_Module_for_Port_Safety/yolov5/data/valid/images/"
number = del_files(path)
print(number)