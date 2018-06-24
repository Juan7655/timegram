import pandas as pd
import matplotlib.pyplot as plt


def run():
	data = pd.read_json("https://pro1-1f859.firebaseio.com/TIMEGRAM_EVENTS.json").T
	data['obj_time'] = pd.to_datetime(data.objective, infer_datetime_format=True, errors='coerce')
	data['real_time'] = pd.to_datetime(data.realtime, infer_datetime_format=True, errors='coerce')
	data['differ'] = data.obj_time - data.real_time
	data['differ_min'] = data.differ.map(lambda x: x.seconds - (0 if x.seconds < 3600 else 3600*24)) / 60
	data['on_time'] = data.differ_min >= 0

	print("Mean arrival time difference: {} minutes".format(data['differ_min'].mean()))
	data['differ_min'].hist()
	plt.show()


if __name__ == "__main__":
	run()
