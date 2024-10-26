set "my_string=sensor_id=t1; value=30"
echo %my_string% | nc -u -w 1 127.0.0.1 3344
set "my_string=sensor_id=t1; value=40.1"
echo %my_string% | nc -u -w 1 127.0.0.1 3344
set "my_string=sensor_id=h1; value=40"
echo %my_string% | nc -u -w 1 127.0.0.1 3355
set "my_string=sensor_id=h1; value=50.1"
echo %my_string% | nc -u -w 1 127.0.0.1 3355