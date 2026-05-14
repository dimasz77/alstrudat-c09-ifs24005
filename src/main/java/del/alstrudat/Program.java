package del.alstrudat;

import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;

public class Program {
    private static class Process {
        String name;
        int priority;
        int entryOrder;

        Process(String name, int priority, int entryOrder) {
            this.name = name;
            this.priority = priority;
            this.entryOrder = entryOrder;
        }
    }

    private Process[] heap;
    private Map<String, Integer> posMap;
    private int size;
    private int capacity;
    private int orderCounter;

    public Program(int initialCapacity) {
        this.capacity = initialCapacity;
        this.heap = new Process[capacity + 1];
        this.posMap = new HashMap<>();
        this.size = 0;
        this.orderCounter = 0;
    }

    private int compare(Process p1, Process p2) {
        if (p1.priority != p2.priority) {
            return Integer.compare(p1.priority, p2.priority);
        }
        return Integer.compare(p1.entryOrder, p2.entryOrder);
    }

    private void swap(int i, int j) {
        Process temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        posMap.put(heap[i].name, i);
        posMap.put(heap[j].name, j);
    }

    private void resize() {
        this.capacity = this.capacity * 2 + 1;
        Process[] newHeap = new Process[this.capacity + 1];
        System.arraycopy(heap, 1, newHeap, 1, size);
        this.heap = newHeap;
    }

    private void percolateUp(int idx) {
        while (idx > 1 && compare(heap[idx], heap[idx / 2]) < 0) {
            swap(idx, idx / 2);
            idx = idx / 2;
        }
    }

    private void percolateDown(int idx) {
        while (2 * idx <= size) {
            int smallest = 2 * idx;
            int right = 2 * idx + 1;
            if (right <= size && compare(heap[right], heap[smallest]) < 0) {
                smallest = right;
            }
            if (compare(heap[smallest], heap[idx]) < 0) {
                swap(idx, smallest);
                idx = smallest;
            } else break;
        }
    }

    public void add(String processName, int priority, PrintWriter out) {
        if (posMap.containsKey(processName)) {
            out.println("ADD FAILED");
            return;
        }
        if (size == capacity) resize();
        size++;
        Process newProc = new Process(processName, priority, orderCounter++);
        heap[size] = newProc;
        posMap.put(processName, size);
        percolateUp(size);
    }

    public void update(String processName, int newPriority, PrintWriter out) {
        Integer idx = posMap.get(processName);
        if (idx == null) {
            out.println("UPDATE FAILED");
            return;
        }
        int oldPriority = heap[idx].priority;
        heap[idx].priority = newPriority;
        if (newPriority < oldPriority) percolateUp(idx);
        else if (newPriority > oldPriority) percolateDown(idx);
    }

    public void kill(String processName, PrintWriter out) {
        Integer idx = posMap.get(processName);
        if (idx == null) {
            out.println("KILL FAILED");
            return;
        }
        out.println("KILLED: " + processName);
        if (idx == size) {
            posMap.remove(processName);
            heap[size--] = null;
        } else {
            swap(idx, size);
            posMap.remove(processName);
            heap[size--] = null;
            percolateUp(idx);
            percolateDown(idx);
        }
    }

    public void serve(PrintWriter out) {
        if (size == 0) {
            out.println("SERVED EMPTY");
            return;
        }
        out.println("SERVED: " + heap[1].name);
        if (size == 1) {
            posMap.remove(heap[1].name);
            heap[1] = null;
            size = 0;
        } else {
            swap(1, size);
            posMap.remove(heap[size].name);
            heap[size--] = null;
            percolateDown(1);
        }
    }

    public void printHeap(PrintWriter out) {
        if (size == 0) {
            out.println("EMPTY");
            return;
        }
        for (int i = 1; i <= size; i++) {
            out.print(heap[i].name + (i == size ? "" : " "));
        }
        out.println();
    }
}

