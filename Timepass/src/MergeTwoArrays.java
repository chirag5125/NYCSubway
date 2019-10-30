
public class MergeTwoArrays {
	
	public static int[] mergeArray(int[] arr, int arr1[]) {
    	int[] array=new int[(arr.length+arr1.length)];
    	
    	for(int i=0;i<arr.length;i++) {
    		array[i]=arr[i];
    	}
    	for(int j=0;j<arr1.length;j++) {
    		array[arr.length+j]=arr1[j];
    	}
    	return array;
    }
    // Driver method 
    public static void main(String args[]) 
    { 
        int arr[] = {12, 11, 13, 5, 6, 7}; 
        int arr1[]= {13,22,0,12,3,4,6};
        
        int array[]=mergeArray(arr, arr1);
        
        for(int i=0;i<array.length;i++) {
        	System.out.print(array[i]+" ");
        }
        
    }

}
