import time
from threading import Thread
import hand as hand
from pycaw.pycaw import AudioUtilities, IAudioEndpointVolume
from comtypes import CLSCTX_ALL


def set_volume(self):
    devices = AudioUtilities.GetSpeakers()
    interface = devices.Activate(IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
    volume = interface.QueryInterface(IAudioEndpointVolume)
    volume.SetMasterVolumeLevelScalar(self.distancePercent / 100, None)
    self.drawDistance()

if __name__ == '__main__':
    hand = hand.handDetector(0.8,1)
    hand.function = set_volume
    hand.run()
