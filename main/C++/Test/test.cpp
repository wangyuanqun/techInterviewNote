#include <iostream>
int main() {
    // You need to use an ifstream if you just want to read (use an ofstream to write, or an fstream for both).
    std::cout << "what is your name and age: ";
    int age;
    std::string name;
    std::cin >> name >> age;

    std::cout << "Nice to meet you " << name << ", and you are " << age << " years old." << std::endl;

    return 0;
}