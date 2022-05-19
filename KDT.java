import java.util.Scanner;

public class KDTreem {
    KDNode root = null;
    static HashMapclass<String,KDNode> hashMap = new HashMapclass<>();

//    public KDTree(KDNode root) {
//        this.root = root;
//    }

    public KDNode searchNode(KDNode root,Point point,boolean align){

        if (root == null) return null;
        if (point.x == root.point.x && point.y == root.point.y)
            return root;

        if (align){
            if (point.x < root.point.x){
                return searchNode(root.left,point,false);
            }
            else {
                return searchNode(root.right,point,false);
            }
        }
        else{
            if (point.y < root.point.y){
                return searchNode(root.left,point,true);
            }
            else {
                return searchNode(root.right,point,true);
            }
        }
    }

    public KDNode addB(KDNode root, Point point, String name) {
        KDNode node = insert(root, point,null,name, true);
        if (node!= null)
            hashMap.put(name,node);
        return node;
    }

    public KDNode insert(KDNode root, Point point,String bankname, String name, boolean align) {

        if (root == null)
            return new KDNode(point,bankname, name, align);

        if (root.isX) {
            if (point.x > root.point.x) {
                root.right = insert(root.right, point,bankname, name, !align);
                root.right.parent = root;
            } else {
                root.left = insert(root.left, point,bankname, name, !align);
                root.left.parent = root;
            }
        } else {
            if (point.y > root.point.y) {
                root.right = insert(root.right, point,bankname, name, !align);
                root.right.parent = root;
            } else {
                root.left = insert(root.left, point,bankname, name, !align);
                root.left.parent = root;
            }
        }
        return root;
    }

    public double findDistance(Point p1, Point p2) {
        double deltaX = p1.x - p2.x;
        double deltaY = p1.y - p2.y;

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public KDNode nearB(KDNode root, Point searchPoint, KDNode minPoint, boolean align) {
        KDNode min = minPoint;

        if (root == null)
            return min;

        double temp = findDistance(searchPoint, root.point);
        double min2 = findDistance(searchPoint, min.point);

//        System.out.println(temp);
//        System.out.println(min2);

        if (min2 >= temp) {
            min = root;
        }

        if (!align) {
            if ((searchPoint.y > root.point.y)) {
                min = nearB(root.right, searchPoint, min, true);
                if (root.left != null && findDistance(min.point, searchPoint) >
                        Math.abs(searchPoint.y - min.point.y)) {
                    min = nearB(root.left, searchPoint, min, true);
                }

            }

            if ((searchPoint.y < root.point.y)) {
                min = nearB(root.left, searchPoint, min, true);
                if (root.right != null &&
                        findDistance(min.point, searchPoint) >
                                Math.abs(searchPoint.y - min.point.y)) {
                    min = nearB(root.right, searchPoint, min, true);
                }

            }

        } else {

            if ((searchPoint.x > root.point.x)) {
                min = nearB(root.right, searchPoint, min, false);
                if (root.left != null &&
                        findDistance(min.point, searchPoint) >
                                Math.abs(searchPoint.x - min.point.x)) {
                    min = nearB(root.left, searchPoint, min, false);
                }

            }

            if ((searchPoint.x < root.point.x)) {
                min = nearB(root.left, searchPoint, min, false);
                if (root.right != null &&
                        findDistance(min.point, searchPoint) >
                                Math.abs(searchPoint.x - min.point.x)) {
                    min = nearB(root.right, searchPoint, min, false);
                }
            }
        }

        return min;
    }


    /*public static void addNeighbourhood(Neighbourhood neighbourHood) {

//        HashMap<String,Point> hashMap = new HashMap<>() ;
        String name = neighbourHood.name;
        Point[] points = new Point[2];
        points[0] = neighbourHood.point1;
        points[1] = neighbourHood.point2;
        hashMap.put(name, points[0]);
        hashMap.put(name, points[1]);
    }*/


    /*public void addN(String name, Point point1, Point point2) {
        Neighbourhood n = new Neighbourhood(name, point1, point2);
        addNeighbourhood(n);
    }*/


    public KDNode addBr(KDNode root,Point point,String bankname,String name) {
        KDNode node = insert(root, point,bankname, name, true);
        KDTreem tree = hashMap.get(bankname).branchs;
        tree.root = tree.insert(tree.root,point,bankname,name,true);
        node.bankName=bankname;
        return node;
    }

    KDNode nearBr (String bankName, Point searchPoint, KDNode minPoint,boolean align) {
        return nearB(hashMap.get(bankName).branchs.root,searchPoint,minPoint,align);
    }

    void listBrs(String bankname){
        KDNode bank = hashMap.get(bankname);
        System.out.println("Bank-Branches: " + bankname);
        listBranches(bank.branchs.root);
    }
    void listBranches(KDNode root){
        if (root==null)
            return;
        System.out.println(root.Name + "(" + root.point.x + "," + root.point.y + ")");
        listBranches(root.left);
        listBranches(root.right);
    }

    public static void main(String[] args) {
        KDTreem tree = new KDTreem();

        //Point point1 = new Point(5,1);
        tree.root = tree.addB(tree.root,new Point(5,1),"Melat");
        tree.root = tree.addB(tree.root,new Point(10,12),"Saderat");
        tree.root = tree.addB(tree.root,new Point(3,15),"Sepah");
        tree.root = tree.addB(tree.root,new Point(8,7),"Melli");
        tree.addBr(tree.root,new Point(6,3),"Melat","ali");
        tree.addBr(tree.root,new Point(3,6),"Melat","rey");
        tree.addBr(tree.root,new Point(7,5),"Melat","rez");
        tree.addBr(tree.root,new Point(4,8),"Melat","zein");
        System.out.println(tree.root);

        tree.listBrs("Melat");
        System.out.println(tree.root.point.x + " " + tree.root.point.y);
        //System.out.println(tree.nearest(tree.root,new Point(9,8),tree.root.point,true).x);
        System.out.println(tree.nearB(tree.root,new Point(4,7),tree.root,true).point.x);
        tree.listBrs("Melat");
//        tree.delBr(tree.root,new Point(7,5));
//
//        tree.listBrs("Melat");

        // Point searchTest = new Point(2,14);

        // System.out.println(tree.nearB(temp,searchTest,temp.point,true).x);
//        System.out.println(temp.left.bankName);


//        System.out.println(temp.right.bankName);
//        System.out.println(temp.right.left.bankName);

        // KDNode temp = root;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String order = scanner.next();
//        if (order.equals("addN")) {
//            System.out.println("Enter a name for neighbourhood");
//            String nameN = scanner.next();
//            System.out.println("Enter locatin of it in 2 important points");
//            Point[] points =new Point[2] ;
//            points[0].x = scanner.nextDouble();
//            points[0].y = scanner.nextDouble();
//            points[1].x = scanner.nextDouble();
//            points[1].y = scanner.nextDouble();
//
//        }
            if (order.equals("addB")) {
                System.out.println("Enter the name for bank");
                String bankname = scanner.next();
                System.out.println("Enter location of Bank");
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                Point point = new Point(x, y);
                tree.addB(null, point, bankname);
            }
            if (order.equals("addBr")) {
                System.out.println("Enter the name for Bank u want to add");
                String bankName = scanner.next();
                System.out.println("Enter the name for Branch");
                String branchName = scanner.next();
                System.out.println("Enter location of Branch");
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                Point point = new Point(x, y);
                tree.addBr(tree.root, point, bankName, branchName);
//            temp = treem.addB(temp,point,branchName) ;
//            treem.addBr(branchName,point);
            }
            /*if (order.equals("delBr")) {
                System.out.println("Enter location of Branch to delete");
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                // Point point = new Point(x, y);
                tree.deleteNode(x,y) ;

            }*/
            if (order.equals("listBrs")) {
                System.out.println("Enter the name of the bank");
                String bankNmae = scanner.next();
                tree.listBrs(bankNmae);
            }
            if (order.equals("nearB")) {
                System.out.println("Enter your location");
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                Point point = new Point(x, y);
                tree.nearB(tree.root, point, tree.root, true);
            }
            if (order.equals("nearBr")) {
                System.out.println("Enter the name of the bank");
                String bankNmae = scanner.next();
                System.out.println("Enter your location");
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                Point point = new Point(x, y);
                tree.nearBr(bankNmae, point, tree.root, true);
            }
        }

    }

    class KDNode {

        public KDNode(Point point, String Name, boolean isX) {
            this.point = point;
            this.Name = Name;
            this.isX = isX;
            left = right = null;
            branchs = new KDTreem();
        }

        public KDNode(Point point,String bankName, String name, boolean isX) {
            this.bankName = bankName;
            this.point = point;
            Name = name;
            this.isX = isX;
            left = right = null;
            branchs = new KDTreem();
        }

        String bankName;
        KDTreem branchs;
        KDNode left, right, parent;
        Point point;
        String Name;
        boolean isX;

    }
}
class Point {
    double x,y;

    // KDTreem.KDNode node ;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
class HashMapclass<K,V> {
    private Entry<K,V>[] table;   //Array of Entry.
    private int capacity = 50;  //Initial capacity of HashMap

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }


    @SuppressWarnings("unchecked")
    public HashMapclass(){
        table = new Entry[capacity];
    }

    public void put(K newKey, V data){
        if(newKey==null)
            return;    //does not allow to store null.

        //calculate hash of key.
        int hash=hash(newKey);
        //create new entry.
        Entry<K,V> newEntry = new Entry<K,V>(newKey, data, null);

        //if table location does not contain any entry, store entry there.
        if(table[hash] == null){
            table[hash] = newEntry;
        }else{
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];

            while(current != null){ //we have reached last entry of bucket.
                if(current.key.equals(newKey)){
                    if(previous==null){  //node has to be insert on first of bucket.
                        newEntry.next=current.next;
                        table[hash]=newEntry;
                        return;
                    }
                    else{
                        newEntry.next=current.next;
                        previous.next=newEntry;
                        return;
                    }
                }
                previous=current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }


    public V get(K key){
        int hash =

                hash(key);
        if(table[hash] == null){
            return null;
        }else{
            Entry<K,V> temp = table[hash];
            while(temp!= null){
                if(temp.key.equals(key))
                    return temp.value;
                temp = temp.next; //return value corresponding to key.
            }
            return null;   //returns null if key is not found.
        }
    }


    public boolean remove(K deleteKey){

        int hash=hash(deleteKey);

        if(table[hash] == null){
            return false;
        }else{
            Entry<K,V> previous = null;
            Entry<K,V> current = table[hash];

            while(current != null){ //we have reached last entry node of bucket.
                if(current.key.equals(deleteKey)){
                    if(previous==null){  //delete first entry node.
                        table[hash]=table[hash].next;
                        return true;
                    }
                    else{
                        previous.next=current.next;
                        return true;
                    }
                }
                previous=current;
                current = current.next;
            }
            return false;
        }

    }



    public void display(){

        for(int i=0;i<capacity;i++){
            if(table[i]!=null){
                Entry<K, V> entry=table[i];
                while(entry!=null){
                    System.out.print("{"+entry.key+"="+entry.value+"}" +" ");
                    entry=entry.next;
                }
            }
        }

    }
    private int hash(K key){
        return Math.abs(key.hashCode()) % capacity;
    }

}