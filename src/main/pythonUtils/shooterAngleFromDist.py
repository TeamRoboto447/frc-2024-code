from networktables import NetworkTables as NT
from time import sleep
import numpy as np
import matplotlib.pyplot as plt


def main():
    NT.initialize(server="10.4.47.2")
    print("Please wait while the NetworkTable connects")
    while not NT.isConnected():
        sleep(0.1)

    table = NT.getTable('pidTuningPVs')
    distance = table.getEntry('distFromTarget')
    angle = table.getEntry('angleOfShooter')

    iteration = 0
    measurements = [[0, 0, 0], [0, 0, 0]]
    while(iteration <= 2):
        input("Press any key to continue")

        if not NT.isConnected():
            print("NT Server disconnected... trying to reconnect")
            NT.initialize(server="10.4.47.2")
            while not NT.isConnected():
                sleep(0.1)
        print("Logging measurements..")
        measurements[0][iteration] = angle.getDouble(-1)
        if measurements[0][iteration] == -1:
            print("Something went wrong in getting angle")
            exit()
        averageX = 0
        sampleSize = 100
        for i in range(sampleSize):
            averageX += distance.getDouble(0)
            sleep(0.02)
        averageX /= sampleSize
        print(averageX)
        measurements[1][iteration] = averageX
        iteration += 1
    
    results = ""
    for i in range(len(measurements)+1):
        results += "Speed: {measurements[0][i]} | X: {measurements[1][i]}\n"

    x = np.array(measurements[1])
    y = np.array(measurements[0])
    b = estimateCoef(x, y)
    plotLine(x, y, b)
    print(f"Angle Value = ({b[1]} * Distance Value) + {b[0]}")


def estimateCoef(x, y):
    # Number of values
    n = np.size(x)
    #print(f"X: {x}\nY:{y}")

    m_x, m_y = np.mean(x), np.mean(y)

    SS_xy = np.sum(y*x) - n*m_y*m_x
    SS_xx = np.sum(x*x) - n*m_x*m_x

    #print(f"SS_xy = {SS_xy}; SS_xx = {SS_xx}")
    b_1 = SS_xy / SS_xx
    b_0 = m_y - b_1*m_x
    
    return (b_0, b_1)

def plotLine(x, y, b):
    plt.scatter(x, y, color="m", marker="o", s=30)

    y_pred = (b[1]*x)+b[0]
    plt.plot(x, y_pred, color="g")

    plt.show()

if __name__ == '__main__':
    main()
