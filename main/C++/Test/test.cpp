#include <iostream>
int main() {
    std::cout << "what is your name and age: ";
    int age = 0;
    std::string name = "";
    std::cin >> name >> age;

    std::cout << "Nice to meet you " << name << ", and you are " << age << " years old." << std::endl;

    return 0;
}