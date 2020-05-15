package components;

public interface Interchangeable {
    String getComponentType();
    int getRpm();
    void setRpm(int rpm);
    int getCapacity();
    void setCapacity(int capacity);
    String getFormFactor();
    void setFormFactor(String formFactor);
    int getCoreRpm();
    void setCoreRpm(int coreRPM);
    int getMaxRpm();
    void setMaxRpm(int maxRPM);
    double getCoreNoise();
    void setCoreNoise(double coreNoise);
    double getMaxNoise();
    void setMaxNoise(double noise);
    double getPowerConsumption();
    void setPowerConsumption(double powerConsumption);
    String getSocket();
    void setSocket(String socket);
    int getCoreCount();
    void setCoreCount(int coreCount);
    double getCoreClock();
    void setCoreClock(double coreClock);
    double getBoostClock();
    void setBoostClock(double boostClock);
    String getBussType();
    void setBussType(String bussType);
    int getMemory();
    void setMemory(int memory);
    String getMemoryType();
    void setMemoryType(String memoryType);
    int getBoostSpeed();
    void setBoostSpeed(int boostSpeed);
    boolean getTactile();
    void setTactile(boolean tactile);
    int getRam();
    void setRam(int ram);
    String getMemoryTech();
    void setMemoryTech(String memoryTech);
    int getSpeed();
    void setSpeed(int speed);
    double getSize();
    void setSize(double size);
    int getRefreshRate();
    void setRefreshRate(int refreshRate);
    int getRamSlots();
    void setRamSlots(int ramSlots);
    int getMaxRamSize();
    void setMaxRamSize(int maxRamSize);
    int getNumberButtons();
    void setNumberButtons(int numberButtons);
    int getDpi();
    void setDpi(int dpi);
    boolean isErgonomic();
    void setErgonomic(boolean ergonomic);
    boolean isWireless();
    void setWireless(boolean wireless);
    int getPowerCapacity();
    void setPowerCapacity(int powerCapacity);
    String getNoise();
    void setNoise(String noise);
    String getRpmString();
    void setRpmString(String newValue);
    String getSpec();
    String getName();
}