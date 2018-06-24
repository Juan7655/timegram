import pandas as pd


def run():
	data = pd.read_json("https://pro1-1f859.firebaseio.com/TIMEGRAM_EVENTS.json").T
	data['obj_time'] = pd.to_datetime(data.objective, infer_datetime_format=True, errors='coerce')
	data['real_time'] = pd.to_datetime(data.realtime, infer_datetime_format=True, errors='coerce')
	data['diff'] = data.obj_time - data.real_time
	print(data)
	print(data['diff'].mean())


if __name__ == "__main__":
	run()
