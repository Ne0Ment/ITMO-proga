package org.main.commands;

import java.io.PrintWriter;

public class HelpCommand implements Command {
    private PrintWriter writer;

    public HelpCommand(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public boolean execute(String[] commandArgs) {
        String helpText = """
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                save : сохранить коллекцию в файл
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить программу (без сохранения в файл)
                add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
                add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
                remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                filter_less_than_position position : вывести элементы, значение поля position которых меньше заданного
                print_descending : вывести элементы коллекции в порядке убывания
                print_unique_organization : вывести уникальные значения поля organization всех элементов в коллекции
                """;
        writer.println(helpText);
        return false;
    }

}
